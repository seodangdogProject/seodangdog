from fastapi import FastAPI
from recommend.content_base_recommend import router as content_base_recommend_router

app = FastAPI()


@app.get("/hello")
def hello():
    return {"message": "Hello! FastAPI!!"}


app.include_router(content_base_recommend_router)