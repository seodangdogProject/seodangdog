from fastapi import APIRouter
import pymysql
import pandas as pd
from faker import Faker
from pymongo import MongoClient
import random
import asyncio
import aiomysql

import json
from bson import json_util
from bson import ObjectId

from repository.news_repository import findNews

router = APIRouter()

# MySQL 연결 설정
db_config = {
    "host": 'seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com',
    "port": 3306,
    "user": 'seodangdog',
    "password": 'dogseodang0311',
    "database": 'seodangdog',
    "charset": 'utf8'
}

# connection = pymysql.connect(host='seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com',
#                              port=3306, user='seodangdog',
#                              password='dogseodang0311',
#                              database='seodangdog',
#                              charset='utf8')
# cursorclass=pymysql.cursors.DictCursor,)


# DB와 접근하는 conn, cur 객체 생성 후 반환
def mysql_create_session():
    connection = pymysql.connect(host=db_config['host'], user=db_config['user'], password=db_config['password'], db=db_config['database'], charset="utf8", port=db_config['port'])
    return connection


# MongoDB
host = "seodangdog"
port = "27017"
username = "dogsoedang"
password = "sssdangorg56"
uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
dbname = 'seodangdog'
client = MongoClient(uri)[dbname]


# @router.get('/fast/test/{user_seq}')
def find_user(user_seq):
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "SELECT * FROM user WHERE user_seq = %s"
            cursor.execute(sql, user_seq)  # 변수들을 리스트에 넣어 전달
            result = cursor.fetchall()
            df = pd.DataFrame(result)
            print(df)
            return result
    except Exception as e:
        print(f"Error occurred find_use: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


# 테스트를 위해 임의의 사용자의 정보를 만든다.
@router.get('/fast/inset_user')
def insert_user():
    limit = 10

    fake = Faker('en_US')
    user_ids = [fake.first_name() for _ in range(limit)]

    fake = Faker('ko_KR')
    user_nicknames = [fake.first_name() for _ in range(limit)]

    user_pwds = [random.randint(1000, 9999) for _ in range(limit)]

    for i in range(0, limit):
        insert_one_user(user_ids[i], user_nicknames[i], user_pwds[i])


# 테스트를 위해 임의의 유저를 mysql에 삽입
def insert_one_user(user_id, nickname, password):
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "INSERT INTO user (user_id, nickname, password) VALUES (%s, %s, %s)"
            cursor.execute(sql, (user_id, nickname, password))
            connection.commit()
            return True
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


# mysql의 모든 유저정보를 가져온다.
def select_all_user():
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "select user_seq, user_id, nickname from user"
            cursor.execute(sql)
            result = cursor.fetchall();
            return result
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


# 몽고디비의 뉴스아이디를 mysql-news테이블의 뉴스시퀀스로 변환(디비에 왔다갔다하지말고 그냥 서버 메모리에 올릴까요)
# def news_id_seq(news_id):
#     connection = mysql_create_session()
#     try:
#         # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
#         with connection.cursor(pymysql.cursors.DictCursor) as cursor:
#             # 파라미터를 사용한 쿼리 실행
#             sql = "select news_seq from news where news_access_id=%s"
#             cursor.execute(sql, news_id)
#             result = cursor.fetchone()
#             result = result['news_seq']
#             # print(result)
#             return result
#     except Exception as e:
#         print(f"Error occurred: {e}")
#         connection.rollback()
#         return False
#     finally:
#         # 연결 종료
#         connection.close()

def select_news_id_seq():
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "select news_seq, news_access_id news_id from news"
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()
# 테스트를 위해 뽑아온 키워드들을 모든 사용자에게 부여한다.
@router.get('/fast/insert_all_user_keyword')
def insert_all_user_keyword():
    print('start -  insert_all_user_keyword')
    limit = 30  # 유저당 넣을 키워드 수
    users = select_all_user()
    for user in users:
        user_seq = user['user_seq']
        weights = [random.randint(1, 10) for _ in range(limit)]
        keywords = pick_keyword(limit)
        for i in range(limit):
            insert_one_user_keyword(user_seq, keywords[i], weights[i])
    print('end -  insert_all_user_keyword')
    return users


# 테스트를 위해 100개의 뉴스에 대하서만 키워드를 가져온다
def get_keyword_top100news():
    top_news = findNews(100)
    result = set()
    for i in top_news:
        for k, v in i['newsKeyword'].items():
            result.add(k)
    print(len(result))
    return list(result)


# 테스트를 위해 가져온 뉴스 키워드중 임의로 30개를 뽑아 사용자에게 부여한다.
def pick_keyword(limit):
    all_keyword = get_keyword_top100news()
    # set에서 랜덤으로 30개의 값을 뽑음
    keyword = random.sample(all_keyword, limit)

    return keyword


# 테스트를위해 사용자 키워드를 디비에 넣는함수
def insert_one_user_keyword(user_seq, keyword, weight):
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "INSERT INTO user_keyword (user_seq, keyword, weight) VALUES (%s, %s, %s)"
            cursor.execute(sql, (user_seq, keyword, weight))
            connection.commit()
            return True
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


# 컨텐츠 기반추천을 할때 가중치를 가진 키워드와 유사한 키워드가 있는 뉴스추천을 진행한다.
@router.get('/fast/select_user_keyword/{user_seq}')
def select_user_keyword(user_seq):
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "select keyword, weight from user_keyword where user_seq = %s"
            cursor.execute(sql, user_seq)
            result = cursor.fetchall()
            return result
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


# 뉴스와 유저가 rating에 있는지 판단
def select_ratings(news_seq, user_seq):
    connection = mysql_create_session()
    try:
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            sql = "select * from ratings where news_seq = %s AND user_seq = %s"
            cursor.execute(sql, (news_seq, user_seq))
            result = cursor.fetchone()
            return result
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


async def async_select_ratings(news_seq, user_seq):
    try:
        async with aiomysql.create_pool(host=db_config['host'], port=db_config['port'], user=db_config['user'],
                                        password=db_config['password'], db=db_config['database']) as pool:
            async with pool.acquire() as conn:
                async with conn.cursor() as cursor:
                    sql = "select * from ratings where news_seq = %s AND user_seq = %s"
                    await cursor.execute(sql, (news_seq, user_seq))
                    result = await cursor.fetchone()
                    return result
    except Exception as e:
        print(f"Error occurred: {e}")
        return False
# 뉴스와 유저가 rating에 없을 경우 삽입
# 추천받은 뉴스를 rating 테이블에 저장한다. MF추천을 받을때 사용한다.


def insert_ratings(rating_data):
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "INSERT INTO ratings (news_seq, user_seq, rating) VALUES (%s, %s, %s)"
            cursor.executemany(sql, [(data[0], data[1], data[2]) for data in rating_data])
            connection.commit()
            return True
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


async def async_insert_ratings(rating_data):
    try:
        async with aiomysql.create_pool(host=db_config['host'], port=db_config['port'], user=db_config['user'],
                                        password=db_config['password'], db=db_config['database'], autocommit=True) as pool:
            async with pool.acquire() as conn:
                async with conn.cursor() as cursor:
                    sql = "INSERT INTO ratings (news_seq, user_seq, rating) VALUES (%s, %s, %s)"
                    await cursor.executemany(sql, rating_data)
                    await conn.commit()
    except Exception as e:
        print(f"Error occurred: {e}")
        return False


# # 뉴스와 유저가 rating에 있을 경우 업데이트
def update_ratings(rating_data):
    connection = mysql_create_session()
    try:
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # rating_data는 (news_seq, user_seq, rating) 튜플의 리스트여야 합니다.
            sql = "UPDATE ratings SET rating = %s WHERE news_seq = %s AND user_seq = %s"
            cursor.executemany(sql, [(data[2], data[0], data[1]) for data in rating_data])
            connection.commit()
            return True
    except Exception as e:
        print(f"Error occurred: {e}")
        connection.rollback()
        return False
    finally:
        connection = mysql_create_session()


async def async_update_ratings(rating_data):
    try:
        async with aiomysql.create_pool(host=db_config['host'], port=db_config['port'], user=db_config['user'],
                                        password=db_config['password'], db=db_config['database'], autocommit=True) as pool:
            async with pool.acquire() as conn:
                async with conn.cursor() as cursor:
                    sql = "UPDATE ratings SET rating = %s WHERE news_seq = %s AND user_seq = %s"
                    await cursor.executemany(sql, rating_data)
                    await conn.commit()
    except Exception as e:
        print(f"Error occurred: {e}")
        return False

# cbf추천을 위해 몽고디비에서 데이터를 들고온다. 뉴스제목, 아이디, 키워드를 들고온다
def get_news_title_keyword():
    response = client.meta_news.find({}, {"_id": 1, "newsTitle": 1, "newsKeyword": 1}).limit(100)
    # result = [doc for doc in response]

    if not response:
        return None
    return response


def select_all_ratings():
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "select user_seq user_id, news_seq news_id, rating from ratings;"
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
    except Exception as e:
        print(f"Error occurred select_all_ratings: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()


# 추천을 위해
def select_all_news():
    connection = mysql_create_session()
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "select news_seq news_id, news_title title from news"
            cursor.execute(sql)
            result = cursor.fetchall()
            return result
    except Exception as e:
        print(f"Error occurred select_all_news: {e}")
        connection.rollback()
        return False
    finally:
        # 연결 종료
        connection.close()