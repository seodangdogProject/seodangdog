from typing import List

import numpy as np
from repository.recommend_repository import select_all_ratings
from repository.recommend_repository import select_all_news
from fastapi import APIRouter
from fastapi import BackgroundTasks
import pandas as pd
import os
from recommend.cbf_recommend import format_weight
from recommend.cbf_recommend import cbf_recommend
from recommend.cbf_recommend import news_id_seq
from repository.recommend_repository import select_ratings
from repository.recommend_repository import update_ratings
from repository.recommend_repository import select_user_news_rating
from repository.recommend_repository import insert_ratings
from repository.recommend_repository import select_news_solved
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


def save_mf(model):
    base_src = './recommend'
    model_name = 'mf_online.pkl'
    save_path = os.path.join(base_src, model_name)
    with open(save_path, 'wb') as f:
        pickle.dump(model, f)


mf = load_mf()


def get_news_title(news_id):
    print(news_id)
    return news.loc[news_id]['title']


def recommend_news(user_seq, mf_model, top_n=21):
    predicted_ratings = [] # 사용자가 풀지 않은 뉴스만 저장
    predicted_ratings_solved=[] # 사용자가 본 뉴스 저장
    solved_news = select_news_solved(user_seq)

    for item_id in mf_model.item_id_index.keys():
        predicted_rating = mf_model.get_one_prediction(user_seq, item_id)

        if item_id not in [sn['news_seq'] for sn in solved_news]:
            predicted_ratings.append((item_id, predicted_rating))
        else:
            predicted_ratings_solved.append((item_id, predicted_rating))
            print("solved ", item_id)

    # 예측 평점을 기준으로 내림차순 정렬
    predicted_ratings.sort(key=lambda x: x[1], reverse=True)
    predicted_ratings_solved.sort(key=lambda x: x[1], reverse=True)

    predicted_ratings = predicted_ratings+predicted_ratings_solved

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


@router.get('/fast/mf_recom/{user_seq}')
async def mf_recommend(background_tasks: BackgroundTasks, user_seq: int):
    start_time = time.time()
    print("mf_recommend")

    if user_seq in mf.user_id_index:
        top_n = 21
        recommendations = recommend_news(user_seq, mf, top_n)

        # 추천목록화인 start
        # print(recommendations)
        # print("Recommendations for user", user_id)
        # for i, news in enumerate(recommendations, 1):
        #     print(f"{i}. {news.news_title} (NEWS SEQ: {news.news_seq}, {news.news_similarity})")
        # 추천목록화인 end

        end_time = time.time()
        execution_time = end_time - start_time
        print(f"mf 추천: {execution_time} 초")

        # rating에 없는걸 추천받으면 넣는다
        insert_task = asyncio.create_task(insert_rating(recommendations, user_seq))

        return recommendations
    else:
        # 방금회원가입했으면(mf 모델에 학습되어 있지 않으면) 추천되지 않는 cbf를 추천하고 mf를 다시 학습시킨다
        print('User not found -> cbf reommend')
        # background_tasks = BackgroundTasks()

        recommended_news = await cbf_recommend(background_tasks, user_seq, False)

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
    info: list


@router.post('/fast/mf_recom/update')
async def mf_update(data: UpdateData):
    user_seq = data.user_seq
    info = data.info
    result = select_user_news_rating(user_seq)
    result = pd.DataFrame(result)

    for i in info:
        news_seq = i[0]
        weight = i[1]

        rating = result[result['news_seq'] == news_seq]

        if len(rating) <= 0:
            print(news_seq, " not exist")
            continue

        print(mf.get_one_prediction(user_seq, news_seq))

        rating = rating['rating'].values[0] * 5

        online_learning(mf, user_seq, news_seq, rating, weight)
        print(mf.get_one_prediction(user_seq, news_seq))

    # 온라인 학습후 업데이트된 모델 저장
    save_mf(mf)
    return {'msg': 'update success'}


async def insert_rating(recommended_news, user_seq):
    start_time = time.time()
    insert_rating_data = []
    for rn in recommended_news:
        news_seq = rn.news_seq
        news_title = rn.news_title
        news_similarity = rn.news_similarity
        info = select_ratings(news_seq, user_seq)

        if info is None:
            temp = [news_seq, user_seq, news_similarity]
            insert_rating_data.append(temp)

    print('insert_rating_data', len(insert_rating_data))
    if len(insert_rating_data) > 0:
        print("MF insert_rating...")
        # print(insert_rating_data)
        insert_ratings(insert_rating_data)

    end_time = time.time()
    execution_time = end_time - start_time
    print(f"MF - 데이터 업데이트완료: {execution_time} 초")
