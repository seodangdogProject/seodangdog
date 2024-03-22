import numpy as np
from pydantic import BaseModel
from fastapi import APIRouter
import pandas as pd
import os
import sys

# sys.path.append(os.path.abspath('src'))
from .content_base_recommend import news_data_objects
from .content_base_recommend import make_user_news_df

router = APIRouter()

content = [
    {
        'news_id': 'A',
        'title': 'A뉴스'
    },
    {
        'news_id': 'B',
        'title': 'B뉴스'
    },
    {
        'news_id': 'C',
        'title': 'C뉴스'
    },
    {
        'news_id': 'D',
        'title': 'D뉴스'
    },
    {
        'news_id': 'E',
        'title': 'E뉴스'
    },
    {
        'news_id': 'F',
        'title': 'F뉴스'
    },
    {
        'news_id': 'G',
        'title': 'G뉴스'
    },
    {
        'news_id': 'H',
        'title': 'H뉴스'
    },
    {
        'news_id': 'I',
        'title': 'I뉴스'
    },
    {
        'news_id': 'J',
        'title': 'J 뉴스'
    }
]
test = [
        {
            'user_id': 1,
            'news_id': 'F',
            'rating': 7
        },
        {
            'user_id': 2,
            'news_id': 'A',
            'rating': 3
        },
        {
            'user_id': 2,
            'news_id': 'B',
            'rating': 3
        },
        {
            'user_id': 3,
            'news_id': 'A',
            'rating': 3
        },
        {
            'user_id': 3,
            'news_id': 'B',
            'rating': 3
        },
        {
            'user_id': 4,
            'news_id': 'B',
            'rating': 5
        },
        {
            'user_id': 4,
            'news_id': 'C',
            'rating': 5
        },
        {
            'user_id': 6,
            'news_id': 'F',
            'rating': 20
        },
        {
            'user_id': 6,
            'news_id': 'H',
            'rating': 20
        },
        {
            'user_id': 6,
            'news_id': 'I',
            'rating': 20
        },
        {
            'user_id': 6,
            'news_id': 'J',
            'rating': 20
        }
]
test_col = ['user_id','news_id','rating']
test_df = pd.DataFrame(test, columns=test_col)

content_df =['news_id','title']
content_df = pd.DataFrame(content, columns=content_df)

print('test_df')
print(test_df)

print('content_df')
print(content_df)
test_df.to_csv('ratings2.csv', sep='|', index=False)
content_df.to_csv('news2.csv', sep='|', index=False)

# 뉴스 데이터프레임
news = {'news_id': [news.id for news in news_data_objects],
        'title': [news.title for news in news_data_objects]}
news_df = pd.DataFrame(news)

# 일치률 데이터프레임
ratings = make_user_news_df()
ratings_df = pd.DataFrame(ratings)


# 데이터셋 만들기# 사용자 기반 이기 때문에 모든 데이터와 user_id를 데이터셋으로 한다
# x = ratings.copy()
# y = ratings['user_id']

@router.get('/fast/cf_recom')
def cf_recom():
    user_ids = list(range(1, 1000))
    users_df = pd.DataFrame({'user_id': user_ids})
    users_df.to_csv('users.csv', sep='|', index=False)
    print(users_df)

    # News 객체를 딕셔너리로 변환
    # news = {'user_id': [news.id for news in news_data_objects],
    #         'title': [news.title for news in news_data_objects]}
    # news_df = pd.DataFrame(news)
    print(news_df)

    # ratings = make_user_news_df()
    # ratings_df = pd.DataFrame(ratings)
    print(ratings_df)
    # print(ratings_df[['user_id','title']])
    # ratings_df.loc[ratings_df['user_id'] == 98]


# 실제값과 예측값을 넣기
def RMSE(y_true, y_pred):
    return np.sqrt(np.mean((np.array(y_true) - np.array(y_pred)) ** 2))


#   # 모델별 RMSE를 계산 하는 함수
def score(model, neighbor_size=0):
    id_pairs = zip(x_test['user_id'], x_test['movie_id'])
    y_pred = np.array([model(user, movie, neighbor_size) for (user, movie) in id_pairs])
    y_true = np.array(x_test['rating'])
    return RMSE(y_true, y_pred)


def test():
    pass
