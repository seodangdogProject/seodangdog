from fastapi import FastAPI
from pymongo import MongoClient
import json

app = FastAPI()

# MongoDB
host = 'seodangdog'
port = 27017
username = 'seodangdog'
password = 'dogseodang0311'
uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
dbname = 'test'
client = MongoClient(uri)[dbname]

# client = MongoClient('mongodb+srv://seodangdog:dogseodang0311@cluster0.vsnd8fy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0')         # URL : mongoDB 의 주소
# db = client.dbsparta
with open('news.json', 'r', encoding="utf8") as f:
    news_data = json.load(f)

@app.get("/hello")
def hello():
    return {"message": "Hello! FastAPI!!"}

@app.get("/saveNews")
def saveNews():
    client["news"].insert_many(news_data)
    # response = client["news"].find_one({"_id": 123})
    # return {"message" : response}
    return {"message": "news saved!"}
    # return {"news" : news_data}