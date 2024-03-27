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


@back_scheduler.scheduled_job('cron', minute="35", hour="3", id='crawling_cron')
def scheduled_job():
    print("띵동. 크론잡입니다. 예정된 시각은 오전 6시 정각이었습니다.")
    print(f"현재 시각은 {(time.localtime().tm_hour+9)%24}시, {time.localtime().tm_min}분입니다.")
    print("당신은 잘하고 있습니다. 그럼 다음에 뵙겠습니다.")
    # crawling_main()
    # save_news()
    # mysql_save()


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
