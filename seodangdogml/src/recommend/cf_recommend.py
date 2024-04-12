import pandas as pd
import os
import numpy as np
from sklearn.model_selection import train_test_split
from repository.recommend_repository import select_all_ratings
# 코사인 유사도 계산
from sklearn.metrics.pairwise import cosine_similarity

ratings = select_all_ratings()

# ratings에 아무것도 없으면 에러 발생(학습데이터가 없기때문에)
ratings = pd.DataFrame(ratings)

# 중복제거(디비의 무결성이 보장되면 필요없음)
ratings = ratings.drop_duplicates(subset=['user_id', 'news_id'])


# 실제값과 예측값을 넣기
def RMSE(y_true, y_pred):
    return np.sqrt(np.mean((np.array(y_true) - np.array(y_pred)) ** 2))


#   # 모델별 RMSE를 계산 하는 함수
def score(model, neighbor_size=0):
    id_pairs = zip(x_test['user_id'], x_test['news_id'])
    y_pred = np.array([model(user, news, neighbor_size) for (user, news) in id_pairs])
    y_true = np.array(x_test['rating'])
    return RMSE(y_true, y_pred)

    # 데이터셋 만들기
    # 사용자 기반 이기 때문에 모든 데이터와 user_id를 데이터셋으로 한다


x = ratings.copy()
y = ratings['user_id']

# stratify 매개변수는 클래스 레이블의 분포를 유지하기 위해 사용되며, 이 과정에서 데이터가 랜덤하게 선택된다
x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.25, stratify=None)

ratings_matrix = x_train.pivot(index='user_id', columns='news_id', values='rating')

## 코사인 유사도를 구하기 위해 rating값을 복제하고, 계산 시 Nan값 에러 대비를 위해 결측치를 0으로 대처
matrix_dummy = ratings_matrix.copy().fillna(0)
## 모든 사용자 간 코사인유사도를 구함
user_similarity = cosine_similarity(matrix_dummy, matrix_dummy)
## 필요한 값 조회를 위해 인덱스 및 칼럼명 지정(예를 들어 1번 유저와 19번 유저의 유사도를 알수있다)
user_similarity = pd.DataFrame(user_similarity,
                               index=ratings_matrix.index, columns=ratings_matrix.index)
### 사용자 평가 경향을 고려한 함수 ###
# full matrix에서 각 사용자의 평점평균을 구한다
rating_mean = ratings_matrix.mean(axis=1)
# 영화 평점과 각 사용자의 평균과의 차이를 구한다
rating_bias = (ratings_matrix.T - rating_mean).T


# 사용자 평가 경향을 고려한 함수
def CF_knn_bias(user_id, news_id, neighbor_size=0):
    if news_id in rating_bias.columns:
        sim_scores = user_similarity[user_id].copy()
        news_ratings = rating_bias[news_id].copy()

        print(news_ratings.isnull())

        # 해당 영화를 평가한 유저만 남기기
        none_rating_idx = sim_scores.loc[news_ratings.isnull()].index
        sim_scores = sim_scores.drop(none_rating_idx)
        news_ratings = news_ratings.dropna()

        if neighbor_size == 0:
            # 가중평가 구하기, 사용자 평가 경향을 고려해야 하기 때문에 예측치를 평점편차로 구했기 때문에 사용자의 평균을 더해줘야한다
            prediction = np.dot(sim_scores, news_ratings) / sim_scores.sum()
            prediction = prediction + rating_mean[user_id]
        else:
            if len(sim_scores) > 1:
                # 이웃크기보다 유사점수를 가진 사용자가 적을수도 있기때문
                neighbor_size = min(neighbor_size, len(sim_scores))
                # 유사도가 높은순대로 뽑아야하기때문에 정렬
                sim_scores = np.array(sim_scores)
                news_ratings = np.array(news_ratings)
                user_idx = np.argsort(sim_scores)
                sim_scores = sim_scores[user_idx][-neighbor_size:]
                news_ratings = news_ratings[user_idx][-neighbor_size:]
                # 실제예측, 평점평균의 편차예측값을 구한것이기 때문에 사용자의 평점평균을 더한다
                prediction = np.dot(sim_scores, news_ratings) / sim_scores.sum();
                prediction = prediction + rating_mean[user_id]

            else:
                # 기존의 3.0이 아니라 사용자의 평가 경향을 고려하여 사용자가 원래 내던 평점평균을 예측값으로 준다
                prediction = rating_mean[user_id]
    else:
        prediction = rating_mean[user_id]
    return prediction


#### 실제 주어진 사용자에 대해 추천을 받는 기능 구현(테스트 데이터와 훈련데이터를 만들필요가없다) ####
ratings_matrix = ratings.pivot_table(values='rating', index='user_id', columns='news_id')
matrix_dummy = ratings_matrix.copy().fillna(0)
user_similarity = cosine_similarity(matrix_dummy, matrix_dummy)
user_similarity = pd.DataFrame(user_similarity, index=ratings_matrix.index, columns=ratings_matrix.index)


def recom_new_news(user_id, n_items, neighbor_size):
    # 해당 유저가 평가한 영화가 나온다
    user_news = ratings_matrix.loc[user_id].copy()
    # print(user_news)

    for news in ratings_matrix.columns:
        # 현재 영화평점이 null이 아닌 경우 -> 영화를 본경우는 추천 리스트에서 제외하기 위해(영화를 보았다 -> 뉴스를 풀었다)
        if pd.notnull(user_news.loc[news]):
            user_news.loc[news] = 0
        else:
            user_news.loc[news] = CF_knn_bias(user_id, news, neighbor_size)

    news_sort = user_news.sort_values(ascending=False)[:n_items]
    recom_news = news.loc[news_sort.index]
    recommendation = recom_news['title']
    return recommendation


def recom_news(user_id, n_items, neighbor_size):
    # 해당 유저가 평가한 영화가 나온다
    user_news = ratings_matrix.loc[user_id].copy()

    # 본것도 추천
    news_sort = user_news.sort_values(ascending=False)[:n_items]
    recom_news = news.loc[news_sort.index]
    recommendation = recom_news['title']
    return recommendation

# recom_new_news(user_id=2, n_items=5, neighbor_size=10)
