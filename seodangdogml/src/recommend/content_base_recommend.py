from pydantic import BaseModel
from fastapi import APIRouter
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

import sys
import time

sys.path.append("/src")
from mongo import getNews

router = APIRouter()


class Item(BaseModel):
    message: str


class News:
    def __init__(self, title, content):
        self.title = title
        self.content = content


allNews = getNews()


@router.post("/recommend_news")
def process_data(item: Item):
    # item 객체를 이용하여 요청 데이터 처리
    # print(item.message)
    news_data_objects = []
    for news in allNews:
        title = news['newsTitle']
        news_data_objects.append(News(title))

    keyword_weights = {"대법원": 1.1, "EU": 1.3}
    user_keywords = ["대법원", "EU"]
    start_time = time.time()
    recommended_news = recommend_news(news_data_objects, user_keywords, keyword_weights)
    end_time = time.time()
    execution_time = end_time - start_time
    print(f"함수 실행 시간: {execution_time} 초")
    print("추천 뉴스:")
    # for title, content, similarity in recommended_news:
    #     print(f"제목: {title} {similarity}")
    return getNews()


def recommend_news(news_data_objects, user_keywords, keyword_weights):
    # 뉴스 데이터프레임 생성
    df_news = pd.DataFrame([[news.title] for news in news_data_objects], columns=['title'])

    # TF-IDF 변환기 생성
    tfidf_vectorizer = TfidfVectorizer()

    print("start")
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
    # recommended_news = [(news_data_objects[i].title, news_data_objects[i].content) for i in recommendation_indices]
    recommended_news = [(news_data_objects[i].title, similarities[0][i]) for i in recommendation_indices]

    return recommended_news

@router.post("/keyword_generator")
def keyword_generator(item: Item):
    news_data_objects = []
    for news in allNews:
        title = news['newsTitle']
        content = news['newsMainText']
        news_data_objects.append(News(title, content))
    # 뉴스 데이터프레임 생성
    df_news = pd.DataFrame([[news.title, news.content] for news in news_data_objects], columns=['title', 'content'])

    # TF-IDF 변환기 생성
    tfidf_vectorizer = TfidfVectorizer()
    # 뉴스 제목과 본문을 합쳐서 벡터화
    corpus = df_news['title'] + ' ' + df_news['content']
    fitted = tfidf_vectorizer.fit(corpus)

    # print(pd.DataFrame(news_vectors.toarray()))
    df = pd.DataFrame(fitted.transform(corpus).toarray())
    df.columns = fitted.vocabulary_
    # for i in range(df.shape[0]):
    df_news['keywords'] = None
    print(df_news[['keywords', 'title']].head(5))
    for i in range(5):
        df_news.loc[[i], ['keywords']] = str(list(df.loc[i].sort_values(ascending=False).index[:20]))
    print(df_news[['keywords', 'title']].head(5))
    return "success"