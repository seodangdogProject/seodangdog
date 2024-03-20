from pydantic import BaseModel
from fastapi import APIRouter
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import random

import time

import os
import sys
sys.path.append(os.path.abspath('src'))
from mongo import getNews
from create_dummy.make_user_data import make_keywordlist

router = APIRouter()


class Item(BaseModel):
    message: str


class News:
    def __init__(self, id, title):
        self.id = id
        self.title = title


class NewsDto:
    def __init__(self, id, title, similarity):
        self.id = id
        self.title = title
        self.similarity=similarity


news_data_objects = []
allNews = getNews()
for news in allNews:
    id = news['_id']['$oid']
    title = news['newsTitle']
    news_data_objects.append(News(id, title))

@router.get("/cbf_recom")
# def process_data(item: Item):
def process_data():
    # keyword_weights = {"北 해킹 의혹": 16.0, "대법원": 1.1, "EU": 1.3, "애플": 2.0}
    keyword_weights = make_keywordlist(50)
    user_keywords = list(keyword_weights.keys())  # 키들을 배열 형태로 반환
    print(keyword_weights)

    start_time = time.time()

    recommended_news = recommend_news(news_data_objects, user_keywords, keyword_weights)

    end_time = time.time()
    execution_time = end_time - start_time
    print(f"함수 실행 시간: {execution_time} 초")

    result = []
    for id, title, similarity in recommended_news:
        result.append(NewsDto(id,title,similarity))
    print(result)
    return result


def recommend_news(news_data_objects, user_keywords, keyword_weights):

    # 뉴스 데이터프레임 생성
    df_news = pd.DataFrame([[news.title] for news in news_data_objects], columns=['title'])
    # TF-IDF 변환기 생성
    tfidf_vectorizer = TfidfVectorizer()

    # 뉴스 제목과 본문을 합쳐서 벡터화
    corpus = df_news['title']
    news_vectors = tfidf_vectorizer.fit_transform(corpus)

    # 벡터화된 데이터를 데이터프레임에 추가
    # df_news['vector'] = [vector.toarray().tolist()[0] for vector in news_vectors]

    # 사용자 키워드를 TF-IDF 벡터로 변환
    user_vector = tfidf_vectorizer.transform([" ".join(user_keywords)])

    # 키워드별 가중치 반영
    # 사용자가 입력한 키워드의 TF-IDF 벡터에서 각 키워드의 인덱스를 찾아 해당 인덱스에 해당하는 원소에 가중치를 곱ㅋㅋㅋ
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
    recommended_news = [(news_data_objects[i].id, news_data_objects[i].title, similarities[0][i]) for i in recommendation_indices[:10]]

    return recommended_news


def make_user_news_df():
    recommended_news_list = []
    random_users = random.sample(range(100), 50)

    for user_id in random_users:
        keyword_weights = make_keywordlist(user_id)
        user_keywords = list(keyword_weights.keys())  # 키들을 배열 형태로 반환

        # print(keyword_weights)

        recommended_news = recommend_news(news_data_objects, user_keywords, keyword_weights)

        for id, title, similarity in recommended_news:
            recommended_news_list.append({'news_id': id, 'user_id': user_id, 'title': title, 'similarity': get_weight(similarity)})

    return recommended_news_list

def get_weight(v):
    v = v*100
    w = round(v, 0)
    int_w = int(w)
    if int_w <= 0: int_w = 1
    return int_w


##########################test##################################################
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
