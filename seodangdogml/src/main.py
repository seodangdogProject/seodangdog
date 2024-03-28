import os
import sched
import time

from fastapi import FastAPI
from apscheduler.schedulers.background import BackgroundScheduler
from contextlib import asynccontextmanager
import uvicorn

# 사용자 라이브러리 추가
from recommend.cbf_recommend import router as cbf_router
from recommend.cf_recommend import router as cf_router
from repository.news_repository import router as news_repo_router
from repository.recommend_repository import router as recommend_repo_router
from recommend.mf_train import router as mf_train_router
from recommend.mf_recommend import router as mf_recommend_router
from repository.news_repository import save_news, mysql_save
from crawling.news_crawling import crawling_main, test


back_scheduler = BackgroundScheduler(timezone='Asia/Seoul')


@back_scheduler.scheduled_job('cron', minute="03", hour="14", id='crawling_cron')
def scheduled_job():
    print("예정된 스케쥴 시작 : crawling_cron")
    crawling_main()
    print("스케쥴 : 크롤링 종료")
    save_news()
    print("스케쥴 : MongoDB, Mysql 저장 종료")
    print("예정된 스케쥴 종료 : crawling_cron")


@asynccontextmanager
async def lifespan(_: FastAPI):
    back_scheduler.start()
    yield


app = FastAPI(lifespan=lifespan)
# app = FastAPI()


if __name__ == "__main__":
    uvicorn.run("main:app", port=5000)


@app.get("/fast/hello")
def hello():
    return {"message": "Hello! FastAPI!!"}

@app.get("/fast/chrome_test")
def chrome_test():
    test()
    return {"message": "chrome_test activated."}


app.include_router(cbf_router)
app.include_router(recommend_repo_router)

app.include_router(cf_router)
app.include_router(news_repo_router)
app.include_router(mf_train_router)
app.include_router(mf_recommend_router)
