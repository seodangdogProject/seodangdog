import os
import sched
import time

from fastapi import FastAPI
from apscheduler.schedulers.background import BackgroundScheduler
from contextlib import asynccontextmanager
import uvicorn

# 사용자 라이브러리 추가
from recommend.cbf_recommend import router as cbf_router, renewal_news_data
from recommend.cf_recommend import router as cf_router
from repository.news_repository import router as news_repo_router
from repository.recommend_repository import router as recommend_repo_router
from recommend.mf_train import router as mf_train_router
from recommend.mf_recommend import router as mf_recommend_router, renewal_news_df
from repository.news_repository import save_news, mysql_save, update_news
from crawling.news_crawling import crawling_main
from recommend.mf_train import load_mf
from gpt_connect.gpt_connection import question_validate


back_scheduler = BackgroundScheduler(timezone='Asia/Seoul')


@back_scheduler.scheduled_job('cron', minute="00", hour="03", id='crawling_cron')
def scheduled_job():
    print("예정된 스케쥴 시작 : crawling_cron")
    crawling_main()
    print("스케쥴 : 크롤링 종료")
    save_news()
    print("스케쥴 : MongoDB 저장 종료")
    mysql_save()
    print("스케쥴 : Mysql 저장 종료")
    update_news()
    print("스케쥴 : 뉴스 문제 저장 종료")
    renewal_news_data()
    renewal_news_df()
    print("스케쥴 : renewal_news_data 종료")
    print("예정된 스케쥴 종료 : crawling_cron")



# mf = load_mf()

@asynccontextmanager
async def lifespan(_: FastAPI):
    back_scheduler.start()

    # cbf추천을 위한 초기데이터 설정(recommend.cbf_recommend)
    renewal_news_data()
    renewal_news_df()
    yield


app = FastAPI(lifespan=lifespan)


if __name__ == "__main__":
    uvicorn.run("main:app", port=5000)


@app.get("/fast/hello")
def hello():
    return {"message": "Hello! FastAPI!!"}


app.include_router(cbf_router)
app.include_router(recommend_repo_router)

app.include_router(cf_router)
app.include_router(news_repo_router)
app.include_router(mf_train_router)
app.include_router(mf_recommend_router)
