# from pydantic import BaseModel
# from fastapi import APIRouter
# import pandas as pd
# from sklearn.feature_extraction.text import TfidfVectorizer
# from sklearn.metrics.pairwise import cosine_similarity
# import random
#
# import time
#
# import os
# import sys
#
# from src.create_dummy.make_user_data import create_user_keyword
# from content_base_recommend import news_data_objects
# from content_base_recommend import make_user_news_df
# router = APIRouter()
#
# users = create_user_keyword()
# u_cols = ['user_id', 'keyword', 'weight']
# # users_df = pd.DataFrame(users, columns=u_cols)
# # print(users_df)
#
# # News 객체를 딕셔너리로 변환
# news = {'id': [news.id for news in news_data_objects],
#         'title': [news.title for news in news_data_objects]}
# # news_df = pd.DataFrame(news)
# # print(news_df)
#
# ratings = make_user_news_df()
# # ratings_df = user_news_df = pd.DataFrame(ratings)
# # print(ratings_df)
# # print(ratings_df[['user_id','title']])
# # ratings_df.loc[ratings_df['user_id'] == 98]
#
print("sssss")