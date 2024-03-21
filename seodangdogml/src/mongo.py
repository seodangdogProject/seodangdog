# 외부 라이브러리 import
from bson import json_util
from pymongo import MongoClient
from fastapi import APIRouter
from konlpy.tag import Okt
import os
import sys
import json
import pymysql
import time
# 사용자 라이브러리 import
sys.path.append(os.path.abspath('src'))
from preprocessing.keyword_generate import keyword_generate


router = APIRouter()


# MongoDB
host="seodangdog"
port="27017"
username="dogsoedang"
password="sssdangorg56"
uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
dbname = 'seodangdog'
client = MongoClient(uri)[dbname]

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
            pos_list_convtd.append(list(pos))
        news_data[i]["newsPos"] = pos_list_convtd
    return news_data


@router.get("/saveNews")
def saveNews():
    print("json 파일 불러오는 중...")
    start_t = time.time()
    with open('news.json', 'r', encoding="utf8") as f:
        news_data = json.load(f)
    news_data = news_data

    # 형태소 저장
    print("형태소 분리 및 저장 중...")
    news_data = morph_sep(news_data)

    # 키워드 추출 및 저장
    print("키워드 추출 및 시작")
    print("본문 키워드 작업 시작")
    news_data = keyword_generate(news_data, "newsMainText", "newsKeyword")
    print("네이버 요약 키워드 작업 시작")
    news_data = keyword_generate(news_data, "newsSummary", "newsSummaryKeyword")

    client["meta_news"].insert_many(news_data)
    return {"message": str(len(news_data)) + " news saved!"}

@router.get("/getNews")
def getNews():
    response = client.meta_news.find({},{"_id": 1, "newsTitle": 1})
    return json.loads(json_util.dumps(response))

@router.get("/getNewsAll")
def getNewsAll():
    response = client.meta_news.find({})
    return json.loads(json_util.dumps(response))

@router.get("/findNews/{limit}")
def findNews(limit):
    response = client.meta_news.find({}).limit(limit)
    return json.loads(json_util.dumps(response))

@router.get("/mysql_save")
def mysql_saveNews():
    # 뉴스 가져오기
    news_data = getNewsAll()

    db = pymysql.connect(host='seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com', port=3306, user='seodangdog', passwd='dogseodang0311', db='seodangdog', charset='utf8')
    #
    cursor = db.cursor()
    sql = (
        f"insert into news (count_solve, count_view, news_access_id, news_created_at, news_description, news_img_url, news_title, media_code, created_at, modified_at) values ")

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
    #
    sql = sql[:-2] + ";"
    result = cursor.execute(sql)
    db.commit()
    db.close()