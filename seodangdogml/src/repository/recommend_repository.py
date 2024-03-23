from fastapi import APIRouter
import pymysql
import pandas as pd
from faker import Faker
import random
from repository.news_repository import findNews

router = APIRouter()

# MySQL 연결 설정
connection = pymysql.connect(host='seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com',
                port=3306, user='seodangdog',
                password='dogseodang0311',
                database='seodangdog',
                charset='utf8')
                # cursorclass=pymysql.cursors.DictCursor,)

# MongoDB
# host="seodangdog"
# port="27017"
# username="dogsoedang"
# password="sssdangorg56"
# uri = f"mongodb://{username}:{password}@j10e104.p.ssafy.io:{port}/{host}?authSource=admin"
# dbname = 'seodangdog'
# client = MongoClient(uri)[dbname]
@router.get('/fast/user/{user_id}')
def find_user(user_id: str):
    try:
        # 트랜잭션과 유사 -> 해당 블록내부는 하나의 트랜잭션으로 간주
        with connection.cursor(pymysql.cursors.DictCursor) as cursor:
            # 파라미터를 사용한 쿼리 실행
            sql = "SELECT * FROM user WHERE user_id = %s"
            cursor.execute(sql, user_id)  # 변수들을 리스트에 넣어 전달
            result = cursor.fetchall()
            df = pd.DataFrame(result)
            print(df)
            return result
    finally:
        # 연결 종료
        # connection.close()
        pass


@router.get('/fast/inset_user')
def insert_user():
    limit = 10

    fake = Faker('en_US')
    user_ids = [fake.first_name() for _ in range(limit)]

    fake = Faker('ko_KR')
    user_nicknames = [fake.first_name() for _ in range(limit)]

    user_pwds = [random.randint(1000, 9999) for _ in range(limit)]

    for i in range(0, limit):
        insert_one_user(user_ids[i],user_nicknames[i],user_pwds[i])


def insert_one_user(user_id, nickname, password):
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
        # connection.close()
        pass

def select_all_user():
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
        # connection.close()
        pass

@router.get('/fast/insert_all_user_keyword')
def insert_all_user_keyword():
    print('start -  insert_all_user_keyword')
    limit = 30 #유저당 넣을 키워드 수
    users = select_all_user()
    for user in users:
        user_seq = user['user_seq']
        weights = [random.randint(1, 10) for _ in range(limit)]
        keywords = pick_keyword(limit)
        for i in range(limit):
            insert_one_user_keyword(user_seq,keywords[i],weights[i])
    print('end -  insert_all_user_keyword')
    return users


def get_keyword_top100news():
    top_news = findNews(100)
    result = set()
    for i in top_news:
        for k,v in i['newsKeyword'].items():
            result.add(k)
    print(len(result))
    return list(result)


def pick_keyword(limit):
    all_keyword = get_keyword_top100news()
    # set에서 랜덤으로 30개의 값을 뽑음
    keyword = random.sample(all_keyword, limit)

    return keyword



def insert_one_user_keyword(user_seq, keyword, weight):
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
        # connection.close()
        pass