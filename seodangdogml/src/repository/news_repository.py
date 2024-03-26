# 외부 라이브러리 import
from bson import json_util
from bson.objectid import ObjectId
from pymongo import MongoClient
from fastapi import APIRouter, HTTPException
from konlpy.tag import Okt
from PIL import Image
import os
import sys
import json
import time
import io
import pymysql
import boto3
from wordcloud import WordCloud
import matplotlib.pyplot as plt
# 사용자 라이브러리 import
sys.path.append(os.path.abspath('src'))
from src.preprocessing.keyword_generate import keyword_generate
# from src.gpt_connect.gpt_connection import question_generate
# 설정 파일
# import settings

router = APIRouter()

# MongoDB
host="seodangdog"
port="27017"
username="dogsoedang"
password="sssdangorg56"
uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
dbname = 'seodangdog'
mongoDB = MongoClient(uri)[dbname]

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
    start_t = time.time()
    with open('news.json', 'r', encoding="utf8") as f:
        news_data = json.load(f)
    # news_data = news_data[:10]

    # 형태소 저장
    print("형태소 분리 및 저장 중...")
    news_data = morph_sep(news_data)

    # 키워드 추출 및 저장
    print("키워드 추출 및 시작")
    print("본문 키워드 작업 시작")
    news_data = keyword_generate(news_data, "newsMainTe xt", "newsKeyword")
    print("네이버 요약 키워드 작업 시작")
    news_data = keyword_generate(news_data, "newsSummary", "newsSummaryKeyword")

    # GPT 문제 저장


    mongoDB["meta_news"].insert_many(news_data)
    return {"message": str(len(news_data)) + " news saved!"}

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

@router.get("/mysql_save")
def mysql_save():
    # 뉴스 가져오기
    news_data = getNewsAll()

    mysqlDB = pymysql.connect(host='seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com', port=3306, user='seodangdog', passwd='dogseodang0311', db='seodangdog', charset='utf8')
    #
    cursor = mysqlDB.cursor()

    sql = (
        f"insert into news (count_solve, count_view, news_access_id, news_created_at, news_description, news_img_url, news_title, media_code, created_at, modified_at) values ")

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
    result = cursor.execute(sql)
    mysqlDB.commit()

    # 전체 키워드 저장
    sql = f"insert into keyword (keyword) values "
    for word in keyword_list:
        sql += f"(\"{word}\"),\n"
    sql = sql[:-2] + ";"
    result = cursor.execute(sql)
    mysqlDB.commit()
    mysqlDB.close()

    # 뉴스별 키워드 저장
    save_keyword_news()


@router.get("/keywordNewsSave")
def save_keyword_news():
    mysqlDB = pymysql.connect(host='seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com', port=3306,
                              user='seodangdog', passwd='dogseodang0311', db='seodangdog', charset='utf8')
    #
    cursor = mysqlDB.cursor()

    # mysql에 저장된 뉴스의 seq, oid 조회
    sql = (f"select news_seq, news_access_id from news;")
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
    mysqlDB.commit()
    mysqlDB.close()

@router.get("/fast/mypages/wordclouds/{user_seq}")
def get_wordcloud(user_seq):
    mysqlDB = pymysql.connect(host='seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com', port=3306,
                              user='seodangdog', passwd='dogseodang0311', db='seodangdog', charset='utf8')
    #
    cursor = mysqlDB.cursor()

    sql = f"select keyword, weight from user_keyword where user_seq = %s order by weight desc;"
    result_cnt = cursor.execute(sql, user_seq)
    sql_result = cursor.fetchall()

    if len(sql_result) == 0:
        return HTTPException(status_code=204, detail="Keywords not found")

    wc = WordCloud(font_path=settings.WC_FONT_PATH, width=800, height=400, background_color="white", colormap="Set1_r")
    cloud = wc.generate_from_frequencies(dict(sql_result))

    session = boto3.Session(
        # aws_access_key_id=settings.S3_ACCESS_KEY,
        # aws_secret_access_key=settings.S3_SECRET_KEY,
        aws_access_key_id="AKIAYS2NT7VYZUUQXEU6",
        aws_secret_access_key="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",
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