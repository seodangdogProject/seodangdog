from fastapi import FastAPI
from recommend.cbf_recommend import router as cbf_router
from recommend.cf_recommend import router as cf_router
from repository.news_repository import router as news_repo_router
from repository.recommend_repository import router as recommend_repo_router
app = FastAPI()


@app.get("/fast/hello")
def hello():
    return {"message": "Hello! FastAPI!!"}


app.include_router(cbf_router)
# app.include_router(recommend_repo_router)
app.include_router(cf_router)
app.include_router(news_repo_router)