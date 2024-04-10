import pandas as pd
import random
import time

from pydantic import BaseModel
from fastapi import APIRouter
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from repository.news_repository import getNews
from create_dummy.make_user_data import make_keywordlist
from repository.recommend_repository import select_user_keyword
from repository.recommend_repository import select_news_id_seq
from repository.recommend_repository import get_news_title_keyword
from repository.recommend_repository import select_ratings
from repository.recommend_repository import select_user_ratings
from repository.recommend_repository import update_ratings
from repository.recommend_repository import insert_ratings
from repository.recommend_repository import select_news_solved
from repository.recommend_repository import async_update_ratings
from repository.recommend_repository import async_insert_ratings
from fastapi import BackgroundTasks
import asyncio

import aiomysql

import numpy as np

from repository.recommend_repository import async_select_ratings

router = APIRouter()


# class Item(BaseModel):
#     message: str


class News:
    def __init__(self, id, title):
        self.id = id
        self.title = title


class NewsDto:
    def __init__(self, news_id, news_seq, news_title, news_similarity,news_keyword):
        self.news_id = news_id
        self.news_seq = news_seq
        self.news_title = news_title
        self.news_similarity = news_similarity
        self.news_keyword = news_keyword


news_data = []
df_news = pd.DataFrame(columns=["keyword"])
news_id_seq = []
tfidf_vectorizer = []
news_vectors=[]


def get_df_news():
    return df_news

def renewal_news_data():
    # 현재는 100개의 뉴스에 대해서만 들고온다 -> 추천되는건 rating테이블에 넣는데 너무 많으면 추천이 잘되는지 확인불가
    global news_data, df_news, news_vectors, tfidf_vectorizer, news_id_seq

    news_id_seq_data = select_news_id_seq()
    result = {entry['news_id']: entry['news_seq'] for entry in news_id_seq_data}
    news_id_seq = result

    data = get_news_title_keyword()
    result = []
    df_result = []
    for doc in data:
        news_id = str(doc['_id'])
        news_title = doc.get('newsTitle')
        news_keyword = doc.get('newsKeyword')
        keyword_str = " ".join(news_keyword.keys())
        news_summary_keyword = doc.get('newsSummaryKeyword')
        temp = {
            "news_id": news_id,
            "news_seq": news_id_seq[news_id],
            "news_title": news_title,
            # "keyword_str": keyword_str,
            # "news_summary_keyword": news_summary_keyword
            "news_keyword": news_keyword
        }

        df_result.append({
            'news_seq': temp['news_seq'],
            'keyword_str': keyword_str,
            'news_keyword': news_keyword
        })
        # print(news_summary_keyword)
        result.append(temp)
    news_data = result

    df_news = pd.DataFrame(df_result, columns=['news_seq', 'keyword_str', 'news_keyword'])
    tfidf_vectorizer = TfidfVectorizer()
    corpus = df_news['keyword_str']
    news_vectors = tfidf_vectorizer.fit_transform(corpus)

    return result

# renewal_news_data는 서버실행시 실행
# get_news_seq는 서버실행시 실행


# BackgroundTasks의 의존성주입
@router.get("/fast/cbf_recom/{user_seq}")
async def cbf_recommend(background_tasks: BackgroundTasks, user_seq: int, flag=True):
    # print('cbf_recommend start')

    user_keyword = select_user_keyword(user_seq)
    # print(user_keyword)
    keyword_weights = {data['keyword']: data['weight'] for data in user_keyword}
    user_keyword_list = list(keyword_weights.keys())  # 키들을 배열 형태로 반환

    # check = dict(sorted(keyword_weights.items(), key=lambda x: x[1]))
    # print(check)

    # start_time = time.time()

    recommended_news = await recommend_news(user_seq, user_keyword_list, keyword_weights, flag)

    # end_time = time.time()
    # execution_time = end_time - start_time
    # print(f"유사도 분석 시간: {execution_time} 초")

    result = []
    for news in recommended_news:
        news_id = news[0]
        news_seq = news_id_seq[news_id]
        news_title = news[1]
        news_similarity = format_weight(news[2])
        # news_similarity = news[2]
        news_summary_keyword = news[3]

        result.append(NewsDto(news_id, news_seq, news_title, news_similarity,news_summary_keyword))

    update_task = asyncio.create_task(update_rating(recommended_news, user_seq))
    # end_time = time.time()
    # execution_time = end_time - start_time
    # print(f"추천완료시간: {execution_time} 초")

    return result


# 가중치에 100을 곱한다.
def format_weight(value):
    return round(value, 10)

# 추천이되면 mysql의 rating에 삽입한다. 시간이 걸리기때문에 backgroundtask로 비동기로 수행
async def update_rating(recommended_news, user_seq):
    start_time = time.time()
    insert_rating_data = []
    update_rating_data = []

    # rating에 유저와 뉴스가 존재하는지 확인하기위함
    user_ratings = select_user_ratings(user_seq)

    for news in recommended_news:
        # print(news)
        news_id = news[0]
        news_seq = news_id_seq[news_id]
        news_title = news[1]
        news_similarity = format_weight(news[2])
        # news_similarity = news[2]
        # info = select_ratings(news_seq, user_seq)

        is_found = False
        for ur in user_ratings:
            if ur['news_seq'] == news_seq:
                is_found = True
                break

        if not is_found:
            temp = [news_seq, user_seq, news_similarity]
            insert_rating_data.append(temp)
        else:
            temp = [news_similarity, news_seq, user_seq]
            update_rating_data.append(temp)

    if len(insert_rating_data) > 0:
        print("insert_rating...")
        # print(insert_rating_data)
        insert_ratings(insert_rating_data)
    if len(update_rating_data) > 0:
        print("update_rating...")
        # print(update_rating_data)
        update_ratings(update_rating_data)

    end_time = time.time()
    execution_time = end_time - start_time
    print(f"cbf - 데이터업데이트완료: {execution_time} 초")


async def recommend_news(user_seq, user_keywords, keyword_weights, flag):
    top_n = 21

    # 뉴스 데이터프레임 생성
    # df_news = pd.DataFrame([[news.title] for news in news_data], columns=
    global news_data, df_news, news_vectors, tfidf_vectorizer


    ### 학습시킨 데이터를 공용으로 사용하기위해 전역변수로 위치시킨다
    # tfidf_vectorizer = TfidfVectorizer()

    # 뉴스키워드를 합친것을 벡터화
    # corpus = df_news['keyword_str']
    # # corpus = df_news['news_title']
    # news_vectors = tfidf_vectorizer.fit_transform(corpus)

    # 사용자 키워드를 TF-IDF 벡터로 변환
    user_vector = tfidf_vectorizer.transform([" ".join(user_keywords)])

    # 키워드별 가중치 반영
    # 사용자가 입력한 키워드의 TF-IDF 벡터에서 각 키워드의 인덱스를 찾아 해당 인덱스에 해당하는 원소에 가중치를 곱
    user_vector_weighted = user_vector.copy()
    for keyword, weight in keyword_weights.items():
        # 키워드의 TF-IDF 벡터의 인덱스 얻기
        keyword_index = tfidf_vectorizer.vocabulary_.get(keyword)
        if keyword_index is not None:
            user_vector_weighted[:, keyword_index] *= weight

    # 각 뉴스와 사용자 키워드 벡터 간의 유사도 계산
    similarities = cosine_similarity(user_vector_weighted, news_vectors)

    # 유사도가 높은 순으로 뉴스 추천
    recommendation_indices = similarities.argsort()[0][::-1]
    # print(recommendation_indices)

    # 사용자가 푼 뉴스 가져오기
    solved_news = select_news_solved(user_seq)  # 사용자가 이미 푼 뉴스를 가져옴
    solved_news_list = [sn['news_seq'] for sn in solved_news]

    # 사용자가 이미 푼 뉴스를 제외하고 추천 리스트에 추가
    filtered_recommendations = []
    solved_recommendations = []
    for i in recommendation_indices:
        news_id = news_data[i]['news_id']
        news_seq = news_id_seq[news_id]
        news_title = news_data[i]['news_title']
        news_keyword = news_data[i]['news_keyword']
        if news_seq not in solved_news_list:
            filtered_recommendations.append((news_id, news_title, similarities[0][i], news_keyword))
        else:
            solved_recommendations.append((news_id, news_title, similarities[0][i], news_keyword))

    recommended_news = filtered_recommendations
    # + solved_recommendations)

    if flag:
        top_21_recommended_news = recommended_news[:top_n]
        return top_21_recommended_news
    else:
        top_42_recommendations = recommended_news[:(top_n * 2)]
        if len(top_42_recommendations) >= top_n:
            next_21_recommendations = top_42_recommendations[top_n:]
            return next_21_recommendations
        else:
            top_21_recommended_news = recommended_news[:top_n]
            return top_21_recommended_news


###########################test##################################################
# def user_news_rating(news_title, user_keywords, keyword_weights):
def user_news_rating(news_title, user_keywords, keyword_weights, max_df=1.0, min_df=1, max_features=None):
    # TF-IDF 변환기 생성
    # tfidf_vectorizer = TfidfVectorizer()

    tfidf_vectorizer = TfidfVectorizer(max_df=max_df, min_df=min_df, max_features=max_features)

    # 사용자 키워드를 TF-IDF 벡터로 변환
    user_vector = tfidf_vectorizer.fit_transform([" ".join(user_keywords)])

    # 키워드별 가중치 반영
    user_vector_weighted = user_vector.copy()
    for keyword, weight in keyword_weights.items():
        keyword_index = tfidf_vectorizer.vocabulary_.get(keyword)
        if keyword_index is not None:
            user_vector_weighted[:, keyword_index] *= weight

    # 뉴스 제목을 TF-IDF 벡터로 변환
    news_vector = tfidf_vectorizer.transform([news_title])

    # 유사도 계산
    similarity = cosine_similarity(user_vector_weighted, news_vector)[0][0]

    return similarity


def make_user_news_df():
    recommended_news_list = []
    random_users = random.sample(range(100), 50)

    for user_id in random_users:
        keyword_weights = make_keywordlist(user_id)
        user_keywords = list(keyword_weights.keys())  # 키들을 배열 형태로 반환

        # print(keyword_weights)

        recommended_news = recommend_news(news_data, user_keywords, keyword_weights)

        # for id, title, similarity in recommended_news:
        #     recommended_news_list.append({'news_id': id, 'user_id': user_id, 'title': title, 'similarity': format_weight(similarity)})

    return recommended_news_list
