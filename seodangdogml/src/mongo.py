import json
from bson import json_util
from pymongo import MongoClient
import pymysql
from fastapi import APIRouter
router = APIRouter()


# MongoDB
host="seodangdog"
port="27017"
username="dogsoedang"
password="sssdangorg56"
uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
dbname = 'seodangdog'
client = MongoClient(uri)[dbname]

@router.get("/saveNews")
def saveNews():
    with open('news.json', 'r', encoding="utf8") as f:
        news_data = json.load(f)
    client["meta_news"].insert_many(news_data)
    return {"message": "news saved!"}



@router.get("/getNews")
def getNews():
    response = client.meta_news.find({},{"_id": 1, "newsTitle": 1})
    return json.loads(json_util.dumps(response))

@router.get("/getNewsAll")
def getNewsAll():
    response = client.meta_news.find({})
    return json.loads(json_util.dumps(response))

@router.get("/mysql")
def conn_test():
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