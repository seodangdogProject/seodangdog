from repository.recommend_repository import select_all_news
from fastapi import APIRouter
from fastapi import BackgroundTasks
import pandas as pd
from recommend.cbf_recommend import cbf_recommend, get_df_news, format_weight
from repository.recommend_repository import select_user_news_rating
from repository.recommend_repository import insert_ratings
from repository.recommend_repository import select_news_solved
from recommend.mf_train import online_learning
from recommend.mf_train import save_mf
from recommend.mf_train import load_mf
from repository.recommend_repository import select_user_ratings

import asyncio
import time
from pydantic import BaseModel

router = APIRouter()

# 뉴스데이터 로드 start

news = []
def renewal_news_df():
    global news
    news = pd.DataFrame(select_all_news())
    news = news.set_index('news_id')


class MfNewsDto:
    def __init__(self, news_seq, news_title, news_similarity, news_keyword):
        self.news_seq = news_seq
        self.news_title = news_title
        self.news_similarity = news_similarity
        self.news_keyword = news_keyword


mf = load_mf()


def get_news_title(news_id):
    return news.loc[news_id]['title']


def recommend_news(user_seq, mf_model, top_n=21):
    predicted_ratings = []  # 사용자가 풀지 않은 뉴스만 저장
    predicted_ratings_solved = []  # 사용자가 본 뉴스 저장
    solved_news = select_news_solved(user_seq)

    solved_news_list = [sn['news_seq'] for sn in solved_news]

    for item_id in mf_model.item_id_index.keys():
        predicted_rating = mf_model.get_one_prediction(user_seq, item_id)
        if item_id not in solved_news_list:
            predicted_ratings.append((item_id, predicted_rating))
        else:
            predicted_ratings_solved.append((item_id, predicted_rating))
            # print("solved ", item_id)

    # 예측 평점을 기준으로 내림차순 정렬
    predicted_ratings.sort(key=lambda x: x[1], reverse=False)
    predicted_ratings_solved.sort(key=lambda x: x[1], reverse=False)

    predicted_ratings = predicted_ratings
    # +predicted_ratings_solved

    # print(predicted_ratings)
    # print("all_recommend_count ", len(predicted_ratings))
    # 상위 top_n개의 영화를 추천 목록에 추가
    df_news = get_df_news()
    recommended_news = []
    # for i in range(min(len(predicted_ratings), len(predicted_ratings))):
    for i in range(min(top_n, len(predicted_ratings))):
        news_seq = predicted_ratings[i][0]
        news_title = get_news_title(news_seq)
        # news_similarity = format_weight(predicted_ratings[i][1])
        news_similarity = format_weight(predicted_ratings[i][1])
        news_keyword= df_news[df_news['news_seq'] == news_seq]['news_keyword'].values[0]
        recommended_news.append(MfNewsDto(news_seq, news_title, news_similarity, news_keyword))

    return recommended_news


@router.get('/fast/mf_recom/{user_seq}')
async def mf_recommend(background_tasks: BackgroundTasks, user_seq: int):
    # start_time = time.time()
    global mf
    mf = load_mf()
    print(mf.user_id_index)
    if user_seq in mf.user_id_index:
        print("mf ",user_seq)
        top_n = 21
        recommendations = recommend_news(user_seq, mf, top_n)
        # print("mf_recommend finished in", len(recommendations))

        # 추천목록화인 start
        # print(recommendations)
        # print("Recommendations for user", user_id)
        # for i, news in enumerate(recommendations, 1):
        #     print(f"{i}. {news.news_title} (NEWS SEQ: {news.news_seq}, {news.news_similarity})")
        # 추천목록화인 end

        # end_time = time.time()
        # execution_time = end_time - start_time
        # print(f"mf 추천: {execution_time} 초")

        # rating에 없는걸 추천받으면 넣는다
        insert_task = asyncio.create_task(insert_rating(recommendations, user_seq))

        for rn in recommendations:
            news_seq = rn.news_seq
            rating = rn.news_similarity
            weight = 1.5
            # print(rn.news_id, rn.news_seq, rn.news_title, rn.news_similarity)
            online_learning(mf, user_seq, news_seq, rating, weight)
        save_mf(mf)

        print("mf_recommend", len(recommendations))
        return recommendations
    # 예외대처(온라인학습으로 했어도 예외발생시 재학습)
    else:
        # 방금회원가입했으면(mf 모델에 학습되어 있지 않으면) 추천되지 않는 cbf를 추천하고 mf를 다시 학습시킨다
        print('User not found -> online learn')
        # background_tasks = BackgroundTasks()

        # cbf 말고 유사한 사용자를 뽑아서 보여주기
        recommended_news = await cbf_recommend(background_tasks, user_seq, False)


        # update_task = asyncio.create_task(train_mf_model())

        # 온라인 학습데이터는 따로 저장할 필요가 없다 -> ratings에 반영하기때문에 재학습시 온라인데이터학습할필요없다.
        # multiprocessing_train()

        for rn in recommended_news:
            news_seq = rn.news_seq
            rating = rn.news_similarity * 10
            weight = 3.0
            # print(rn.news_id, rn.news_seq, rn.news_title, rn.news_similarity)
            online_learning(mf, user_seq, news_seq, rating, weight)
        save_mf(mf)
        return recommended_news


class UpdateData(BaseModel):
    user_seq: int
    info: list


@router.post('/fast/mf_recom/update')
async def update(data: UpdateData):
    return await mf_update(data)


async def mf_update(data):
    print('mf-recommend.py ->  online learning')
    print(mf.user_id_index)
    user_seq = data.user_seq
    info = data.info

    result = select_user_news_rating(user_seq)
    result = pd.DataFrame(result)
    print("info", len(info))
    # 이미 추천된 비율에대가 예측치를 곱해서 온라인학습을시킨다.
    for i in info:
        print(i)
        news_seq = i['news_seq']
        weight = i['weight']

        rating = result[result['news_seq'] == news_seq]

        if len(rating) <= 0:
            print(news_seq, " not exist")
            continue

        # 사용자가 보지 않는 뉴스는(추천할게없어 무작위로 추천받은건 rating이 0이다) update하면 없는 아이템이나 사용자경향을 무작위로 선택한다
        # 그리고 get_one_prediction이 오는데 그건 0이 아니다
        # 그렇다고 또 추천받으면? cbf는 업데이트되는데 mf는 안한다
        # print(mf.get_one_prediction(user_seq, news_seq))
        print("rating : ", rating['rating'].values[0])

        if weight >= 1:
            rating = rating['rating'].values[0] * 20
        else:
            rating = rating['rating'].values[0]
        print("rating : ", rating)
        online_learning(mf, user_seq, news_seq, rating, weight)
        print(mf.get_one_prediction(user_seq, news_seq))

    #     뉴스를 저장하지말고!!!!!! 키워드를 저장해서 cbf를 돌? 에반데
    # 온라인 학습후 업데이트된 모델 저장
    save_mf(mf)
    return {'msg': 'update success'}




async def insert_rating(recommended_news, user_seq):
    start_time = time.time()
    insert_rating_data = []

    # rating에 유저와 뉴스가 존재하는지 확인하기위함
    user_ratings = select_user_ratings(user_seq)

    for rn in recommended_news:
        news_seq = rn.news_seq
        news_title = rn.news_title
        news_similarity = format_weight(rn.news_similarity)
        # info = select_ratings(news_seq, user_seq)

        is_found = False
        for ur in user_ratings:
            if ur['news_seq'] == news_seq:
                is_found = True
                break

        if not is_found:
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
