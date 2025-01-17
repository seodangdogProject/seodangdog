# 외부 라이브러리 import
from datetime import datetime

from bson import json_util
from bson.objectid import ObjectId
from dateutil.relativedelta import relativedelta
from pymongo import MongoClient
from fastapi import APIRouter, HTTPException
from konlpy.tag import Okt
from PIL import Image
from wordcloud import WordCloud
import os
import sys
import json
import time
import io
import pymysql
import boto3
# 사용자 라이브러리 import
sys.path.append(os.path.abspath('src'))
from preprocessing.keyword_generate import keyword_generate
from gpt_connect.gpt_connection import question_generate, question_validate, question_refine
# 설정 파일
import settings

router = APIRouter()

# MongoDB

mongoDB = MongoClient(settings.MONGO_URI)[settings.MONDGO_DBNAME]
def mysql_create_session():
    connection = pymysql.connect(host=settings.MYSQL["host"], port=settings.MYSQL["port"], user=settings.MYSQL["user"], passwd=settings.MYSQL["passwd"], db=settings.MYSQL["db"], charset=settings.MYSQL["charset"])
    return connection


def repo_time_calc(days):
    now = datetime.now()
    before_date = now - relativedelta(days=days)
    before_date_str = before_date.strftime('%Y-%m-%d')
    return before_date_str


def morph_sep(news_data):
    ### 형태소 분리
    okt = Okt()
    for i in range(len(news_data)):
        # for i in range(3,4):
        main_text = news_data[i]["newsMainText"]
        # 띄어쓰기 포함 형태소 분리
        pos_list = []
        for phrase in main_text.split(" "):
            pos_list += okt.pos(phrase)
            pos_list.append((" ", "Whitespace"))

        pos_list_convtd = []
        for pos in pos_list:
            pos_list_convtd.append({"word" : pos[0], "pos" : pos[1]})
        news_data[i]["newsPos"] = pos_list_convtd
    return news_data


@router.get("/save_news")
def save_news():
    print("json 파일 불러오는 중...")
    with open('fast_resources/news.json', 'r', encoding="utf8") as f:
        news_data = json.load(f)

    # 형태소 저장
    print("형태소 분리 및 저장 중...")
    news_data = morph_sep(news_data)

    # 키워드 추출 및 저장
    print("본문 키워드 추출 시작")
    news_data = keyword_generate(news_data, "newsMainText", "newsKeyword")

    print("중복 기사 배제 시작")
    news_data = get_unique_news(news_data)

    print("네이버 요약 키워드 추출 시작")
    news_data = keyword_generate(news_data, "newsSummary", "newsSummaryKeyword")

    # MongoDB 저장
    mongoDB["meta_news"].insert_many(news_data)


@router.get("/update_news")
def update_news():
    # 기존의 문제 변경
    print("update news start")
    news_data = json.loads(json_util.dumps(
        mongoDB.meta_news.find({
            "newsQuiz": {
                "$exists": True
            },
            "ver" : {
                "$exists": False
            }
        })
    ))

    print(f"기존의 문제를 저장한 뉴스 {len(news_data)}개 확인")
    cnt = 0

    for news in news_data:
        stop_check_flag = False
        update_data = {
            "$set": {
                "ver": 2
            }
        }
        updated_quiz = []
        for question in news["newsQuiz"]:
            if question_validate(question):         # 기존 문제 검증
                updated_quiz.append(question_refine(question))
            # 불합격 시 문제 재생성
            else:
                updated_quiz = question_generate(news)["newsQuiz"]
                stop_check_flag = True
            if stop_check_flag: break

        # 몽고 DB 수정
        update_data["$set"]["newsQuiz"] = updated_quiz
        # print(update_data)
        mongoDB.meta_news.update_one({"_id": ObjectId(news["_id"]["$oid"])}, update_data)
        cnt += 1
        if cnt % 100 == 0: print(f"{cnt}개 뉴스의 문제 수정 완료.")

    print("기존 문제 수정 완료")

    news_data = json.loads(json_util.dumps(
        mongoDB.meta_news.find({
            "newsQuiz": {
                "$exists": False
            },
            "ver": {
                "$exists": False
            }
        })
    ))

    print(f"문제가 없는 뉴스 {len(news_data)}개 확인")
    cnt = 0

    for news in news_data:
        update_data = {
            "$set": {
                "ver": 2,
                "newsQuiz": question_generate(news)["newsQuiz"]
            }
        }
        mongoDB.meta_news.update_one({"_id": ObjectId(news["_id"]["$oid"])}, update_data)

def get_unique_news(news_data):
    deleted_news_index = []

    for i in range(len(news_data)):
        if i in deleted_news_index: continue
        target_news = news_data[i]
        for j in range(len(news_data)):
            if(i==j): continue
            compare_news = news_data[j]
            if(len(set(target_news["newsKeyword"].keys()) & set(compare_news["newsKeyword"].keys())) >= 10):
                deleted_news_index.append(j)

    news_data = [news for i, news in enumerate(news_data) if i not in deleted_news_index]
    return news_data

@router.get("/delete_duplicated_news")
def delete_duplicated_news():
    deleted_news_id_list = []
    deleted_news_oid_list = []
    deleted_news_index = []
    # 몽고 DB에서 삭제하기
    response = mongoDB.meta_news.find({}, {"_id": 1, "news_url":1, "newsKeyword": 1})
    news_data = json.loads(json_util.dumps(response))

    print(f"{len(news_data)}개 뉴스 불러오기 완료.")

    for i in range(len(news_data)):
        if i in deleted_news_index: continue
        target_news = news_data[i]
        for j in range(len(news_data)):
            if(i==j): continue
            compare_news = news_data[j]
            if(len(set(target_news["newsKeyword"].keys()) & set(compare_news["newsKeyword"].keys())) >= 10):
                # print("============")
                # print(target_news["news_url"])
                # print(target_news["newsKeyword"].keys())
                # print("----")
                # print(compare_news["news_url"])
                # print(compare_news["newsKeyword"].keys())
                deleted_news_id_list.append(compare_news["_id"]["$oid"])
                deleted_news_oid_list.append(ObjectId(compare_news["_id"]["$oid"]))
                deleted_news_index.append(j)

    print(f"{len(deleted_news_oid_list)}개의 중복 뉴스 확인. 삭제하시겠습니까? (y/n)")
    confirm = input()
    confirm = 0

    while confirm not in ['y','n']:
        print(f"{len(deleted_news_oid_list)}개의 중복 뉴스 확인. 삭제하시겠습니까? (y/n)")
        confirm = input()

        if confirm == 'n':
            print("중복 기사 삭제를 취소하였습니다.")
            return
        if confirm == 'y':
            print("삭제를 진행합니다.")
            break

    result = mongoDB.meta_news.delete_many({"_id": {"$in":deleted_news_oid_list}})

    mysqlDB = mysql_create_session()
    cursor = mysqlDB.cursor()

    sql = (f"select news_seq from news where news_access_id in (")
    for news_seq in deleted_news_id_list:
        sql += f"{news_seq},"
    sql = sql[:-1] + ");"
    cursor.execute(sql)
    news_seq_list = cursor.fetchall()

    # sql = (f"delete from keyword_news where news_seq in (")
    # for news_seq in news_seq_list:
    #     sql += f"{news_seq},"
    # sql = sql[:-1] + ");"
    # cursor.execute(sql)
    #
    # sql = (f"delete from user_news where news_seq in (")
    # for news_seq in news_seq_list:
    #     sql += f"{news_seq},"
    # sql = sql[:-1] + ");"
    # cursor.execute(sql)

    sql = (f"delete from news where news_access_id in (")
    for news_seq in news_seq_list:
        sql += f"{news_seq},"
    sql = sql[:-1] + ");"
    cursor.execute(sql)
    mysqlDB.commit()
    mysqlDB.close()


@router.get("/getNews")
def getNews():
    response = mongoDB.meta_news.find({}, {"_id": 1, "newsTitle": 1})
    return json.loads(json_util.dumps(response))


@router.get("/getNewsAll")
def getNewsAll():
    response = mongoDB.meta_news.find({})
    return json.loads(json_util.dumps(response))

@router.get("/findNews/{limit}")
def findNews(limit):
    response = mongoDB.meta_news.find({}).limit(limit)
    return json.loads(json_util.dumps(response))


def mysql_save():
    news_data = json.loads(json_util.dumps(mongoDB.meta_news.find({"newsCreatedAt" : {"$regex" : f"^{repo_time_calc(1)}"}})))

    mysqlDB = mysql_create_session()
    cursor = mysqlDB.cursor()

    sql = (f"insert into news (count_solve, count_view, news_access_id, news_created_at, news_description, "
           f"news_img_url, news_title, media_code, created_at, modified_at) values ")

    keyword_list = set()
    for news in news_data:
        splited = news["newsMainText"].split("\n\n")
        mainText = ""
        for j in range(len(splited)):
            if (j >= 1): break
            mainText += splited[j] + "."

        mainText = mainText.replace("\'", "%\\\'")
        mainText = mainText.replace("\"", "%\\\"")
        news['newsTitle'] = news['newsTitle'].replace("\'", "%\\\'")
        news['newsTitle'] = news['newsTitle'].replace("\"", "%\\\"")

        sql += f"(0, 0, \"{news['_id']['$oid']}\", str_to_date(\"{news['newsCreatedAt']}\", '%Y-%m-%d %H:%i:%s'), \"{mainText}\", \"{news['newsImgUrl']}\", \"{news['newsTitle']}\", \"{news['media']['mediaCode']}\", now(), now()),\n"

        keyword_list.update(news['newsKeyword'])
        keyword_list.update(news['newsSummaryKeyword'])
    sql = sql[:-2] + ";"
    cursor.execute(sql)

    # 전체 키워드 저장
    sql = "select * from keyword;"
    cursor.execute(sql)
    result = cursor.fetchall()
    sql = f"insert into keyword (keyword) values "
    for word in keyword_list:
        if (word,) not in result:
            sql += f"(\"{word}\"),\n"
    sql = sql[:-2] + ";"
    cursor.execute(sql)

    # 뉴스별 키워드 저장
    save_keyword_news(cursor)
    mysqlDB.commit()
    mysqlDB.close()


@router.get("/keywordNewsSave")
def save_keyword_news(cursor):

    # mysql에 저장된 뉴스의 seq, oid 조회
    sql = (f"select news_seq, news_access_id from news where news_created_at like '{repo_time_calc(1)}%'")
    cursor.execute(sql)

    sql = f"insert into keyword_news (keyword, news_seq) values "
    for mysql_news in cursor:
        # id로 몽고의 keyword 가져오기
        mongo_news = mongoDB.meta_news.find_one({'_id': ObjectId(mysql_news[1])})
        keyword_list = mongo_news["newsKeyword"].keys()
        for keyword in keyword_list:
            sql += f"(\"{keyword}\", \"{mysql_news[0]}\"),\n"

    sql = sql[:-2] + ";"
    cursor.execute(sql)

@router.get("/fast/mypages/wordclouds/{user_seq}")
def get_wordcloud(user_seq):

    mysqlDB = mysql_create_session()
    cursor = mysqlDB.cursor()

    sql = f"select keyword, weight from user_keyword where user_seq = %s order by weight desc;"
    result_cnt = cursor.execute(sql, user_seq)
    sql_result = cursor.fetchall()
    mysqlDB.close()

    if len(sql_result) == 0:
        return HTTPException(status_code=204, detail="Keywords not found")

    wc = WordCloud(font_path=settings.WC_FONT_PATH, width=800, height=400, background_color="white", colormap="Set1_r")
    cloud = wc.generate_from_frequencies(dict(sql_result))

    session = boto3.Session(
        aws_access_key_id=settings.S3_ACCESS_KEY,
        aws_secret_access_key=settings.S3_SECRET_KEY,
    )

    # Save tweet list to an s3 bucket
    FILE_NAME = f"wordcloud/{user_seq}/wordcloud.png"
    s3 = session.resource('s3')
    object = s3.Object(settings.S3_BUCKET_NAME, FILE_NAME)

    image_byte = image_to_byte_array(cloud.to_image())
    object.put(Body=image_byte)

    image_url = f'https://{settings.S3_BUCKET_NAME}.s3.{settings.S3_LOCATION}.amazonaws.com/{FILE_NAME}'

    return image_url


def image_to_byte_array(image: Image, format: str = 'png'):
    result = io.BytesIO()
    image.save(result, format=format)
    result = result.getvalue()

    return result


@router.get("/upload_badge_img/{file_name}")
def upload_badge_img(file_name):
    client_s3 = boto3.client(
        's3',
        aws_access_key_id=settings.S3_ACCESS_KEY,
        aws_secret_access_key=settings.S3_SECRET_KEY
    )
    try:
        client_s3.upload_file(
            f"C:\\Users\\SSAFY\\Pictures\\badge\\{file_name}",
            'seodangdog-s3',
            f"badges/{file_name}",
            ExtraArgs={'ContentType': 'image/jpeg'}
        )
    except Exception as e:
        print(f"Another error => {e}")