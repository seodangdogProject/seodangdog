import os

from fastapi import FastAPI
from pymongo import MongoClient
import json
from recommend.content_base_recommend import router as content_base_recommend_router

app = FastAPI()


# MongoDB
host="seodangdog"
port="27017"
username="seodangdog"
password="dogseodang0311"
uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
dbname = 'test'
client = MongoClient(uri)[dbname]


with open('news.json', 'r', encoding="utf8") as f:
    news_data = json.load(f)


@app.get("/hello")
def hello():
    return {"message": "Hello! FastAPI!!"}


@app.get("/saveNews")
def saveNews():
    client["news"].insert_many(news_data)
    return {"message": "news saved!"}


@app.get("/getNews")
def getNews():
    response = client.news.find({"newsCreatedAt": "2024-03-17 21:03:01"})
    return {"message": response}


app.include_router(content_base_recommend_router)