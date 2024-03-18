import json
from pymongo import MongoClient
from fastapi import APIRouter
router = APIRouter()


# MongoDB
host="seodangdog"
port="27017"
username="seodangdog"
password="dogseodang0311"
uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
dbname = 'test'
client = MongoClient(uri)[dbname]


@router.get("/saveNews")
def saveNews():
    with open('news.json', 'r', encoding="utf8") as f:
        news_data = json.load(f)
    client["news"].insert_many(news_data)
    return {"message": "news saved!"}


@router.get("/getNews")
def getNews():
    response = client.news.find({"newsCreatedAt": "2024-03-17 21:03:01"})
    return {"message": response}
