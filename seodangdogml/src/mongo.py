import json
from bson import json_util
from pymongo import MongoClient
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