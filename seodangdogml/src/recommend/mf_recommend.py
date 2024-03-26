from typing import List

from repository.recommend_repository import select_all_ratings
from repository.recommend_repository import select_all_news
from fastapi import APIRouter
from fastapi import BackgroundTasks
import pandas as pd
import os
from recommend.cbf_recommend import format_weight
from recommend.cbf_recommend import cbf_recommend
from recommend.mf_train import multiprocessing_train
from recommend.mf_train import online_learning
import asyncio
import pickle
import time
from pydantic import BaseModel

router = APIRouter()

# 뉴스데이터 로드 start
news = select_all_news()
news = pd.DataFrame(news)
news = news.set_index('news_id')

class MfNewsDto:
    def __init__(self, news_seq, news_title, news_similarity):
        self.news_seq = news_seq
        self.news_title = news_title
        self.news_similarity = news_similarity


def load_mf():
    base_src = './recommend'
    model_name = 'mf_online.pkl'
    save_path = os.path.join(base_src, model_name)
    with open(save_path, 'rb') as f:
        model = pickle.load(f)
    return model


mf = load_mf()


def get_news_title(news_id):
    return news.loc[news_id]['title']


def recommend_news(user_id, mf_model, top_n=5):
    # 해당 사용자가 평가하지 않은 영화들의 예측 평점을 계산
    predicted_ratings = []

    for item_id in mf_model.item_id_index.keys():
        if item_id not in mf_model.user_id_index.keys():
            predicted_rating = mf_model.get_one_prediction(user_id, item_id)
            predicted_ratings.append((item_id, predicted_rating))

    # 예측 평점을 기준으로 내림차순 정렬
    predicted_ratings.sort(key=lambda x: x[1], reverse=True)
    # print(predicted_ratings)
    # print("all_recommend_count ", len(predicted_ratings))
    # 상위 top_n개의 영화를 추천 목록에 추가
    recommended_news = []
    for i in range(min(top_n, len(predicted_ratings))):
        news_seq = predicted_ratings[i][0]
        news_title = get_news_title(news_seq)
        # news_similarity = format_weight(predicted_ratings[i][1])
        news_similarity = predicted_ratings[i][1]
        recommended_news.append(MfNewsDto(news_seq, news_title, news_similarity))
    return recommended_news


@router.get('/fast/mf_recom/{user_id}')
async def mf_recommend(background_tasks: BackgroundTasks, user_id: int):
    start_time = time.time()
    print("mf_recommend")

    if user_id in mf.user_id_index:
        top_n = 10
        recommendations = recommend_news(user_id, mf, top_n)

        # 추천목록화인 start
        # print(recommendations)
        # print("Recommendations for user", user_id)
        # for i, news in enumerate(recommendations, 1):
        #     print(f"{i}. {news.news_title} (NEWS SEQ: {news.news_seq}, {news.news_similarity})")
        # 추천목록화인 end

        end_time = time.time()
        execution_time = end_time - start_time
        print(f"mf 추천: {execution_time} 초")

        return recommendations
    else:
        # 방금회원가입했으면(mf 모델에 학습되어 있지 않으면) 추천되지 않는 cbf를 추천하고 mf를 다시 학습시킨다
        print('User not found -> cbf reommend')
        # background_tasks = BackgroundTasks()

        recommended_news = await cbf_recommend(background_tasks, user_id, False)

        start_time = time.time()
        # update_task = asyncio.create_task(train_mf_model())
        multiprocessing_train()

        end_time = time.time()
        execution_time = end_time - start_time
        print(f"mf 훈련시간: {execution_time} 초")
        return recommended_news


# new_samples 예제
# new_samples = [
#     (mf.user_id_index[2], mf.item_id_index['J'], 9),
#     (mf.user_id_index[3], mf.item_id_index['J'], 9)
#     ]
class UpdateData(BaseModel):
    user_seq: int
    news_seq: int
    similarity: float
    weight: float


@router.post('/fast/mf_recom/update')
def mf_update(datas: List[UpdateData]):
    for data in datas:
        user_index = mf.user_id_index[data.user_seq]
        news_index = mf.item_id_index[data.news_seq]
        rating = data.similarity * 5
        weight = data.weight
        user_news_weight = (user_index, news_index, rating)
        online_learning(mf,user_news_weight, weight)
# 온라인데이터도 저장해야한다. (csv, 테이블?)