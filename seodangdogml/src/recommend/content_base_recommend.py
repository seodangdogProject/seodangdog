from pydantic import BaseModel
from fastapi import APIRouter
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

import sys

sys.path.append("/src")
from mongo import getNews

router = APIRouter()


class Item(BaseModel):
    message: str


class News:
    def __init__(self, title, content):
        self.title = title
        self.content = content


@router.post("/recommend_news")
def process_data(item: Item):
    # item 객체를 이용하여 요청 데이터 처리
    # print(item.message)
    news_data_objects = []
    allNews = getNews()
    for news in allNews:
        title = news['newsTitle']
        content = news['newsMainText']
        news_data_objects.append(News(title,content))

    keyword_weights = {"대법원": 1.1, "EU": 1.3}
    user_keywords = ["대법원", "EU"]
    recommended_news = recommend_news(news_data_objects, user_keywords, keyword_weights)
    print("추천 뉴스:")
    for title, content, similarity in recommended_news:
        print(f"제목: {title} {similarity}")
    return getNews()


def recommend_news(news_data_objects, user_keywords, keyword_weights):
    # 뉴스 데이터프레임 생성
    df_news = pd.DataFrame([[news.title, news.content] for news in news_data_objects], columns=['title', 'content'])

    # TF-IDF 변환기 생성
    tfidf_vectorizer = TfidfVectorizer()

    # 뉴스 제목과 본문을 합쳐서 벡터화
    corpus = df_news['title'] # + ' ' + df_news['content']
    news_vectors = tfidf_vectorizer.fit_transform(corpus)

    # 벡터화된 데이터를 데이터프레임에 추가
    df_news['vector'] = [vector.toarray().tolist()[0] for vector in news_vectors]

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
    # similarities = cosine_similarity(user_vector_weighted, news_vectors)

    # 유사도가 높은 순으로 뉴스 추천
    # recommendation_indices = similarities.argsort()[0][::-1]
    # recommended_news = [(news_data_objects[i].title, news_data_objects[i].content) for i in recommendation_indices]
    # recommended_news = [(news_data_objects[i].title, news_data_objects[i].content, similarities[0][i]) for i in recommendation_indices]

    return []
