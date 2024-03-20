from faker import Faker
import random
import pandas as pd


# user_id를 요청하면 키워드 + 가중치 리스트 반환
def create_user_keyword():
    fake = Faker()

    # 경제와 관련된 키워드 리스트

    economic_keywords = [
        '증권가', '가상계좌', '국제선', '엔비디아', '반도체', 'LG전자',
        '가공식품', '품목', '대출', 'TSMC', '매수', '애플', '국내증시',
        '공시가', 'FOMC', '코스피', '아파트', 'NCC', '서울살이기준금리',
        'ETF', '트럼프', '도로', '포스코', '나스닥', '김치프리미엄', '토스'
        '이마트', '슈퍼스타', '의대', '의료계', '시스템', '레임덕', '온라인',
        '캠프', '총선', '예비군', '의원', '공천', '민주당', '교육계', '의료계',
        '민생정책', '엔테크', '예금', '통화', '수수료', '애경산업', '투자', 'OECD',
        '보안업무', 'SK', '인하', '상장', '압수수색', '증권', '우리은행', 'ELS'
        ]
        # 더미 데이터 생성
    dummy_data = []

    for i in range(1, 1000):  # 총 1000개의 더미 데이터 생성
        user_id = fake.random_number(digits=3)  # 랜덤한 유저 아이디 생성
        keyword = random.choice(economic_keywords)  # 경제와 관련된 랜덤한 키워드 선택
        weight = random.randint(1, 30)  # 가중치는 1부터 30 사이의 랜덤 값

        # if len(dummy_data) == 0:
        #     dummy_data.append((user_id, keyword, weight))
        # # ID와 키워드가 모두 중복되는 경우에 가중치 증가
        # for index, data in enumerate(dummy_data):
        #     if user_id == data[0] and keyword == data[1]:
        #         dummy_data[index] = (user_id, keyword, data[2] + 3)
        #         break
        #     dummy_data.append((user_id, keyword, weight))
        dummy_data.append((user_id, keyword, weight))
    # for data in enumerate(dummy_data):
    #     if data[1][0] == 50:
    #         print(data[1][1], data[1][2])
    # print(dummy_data)
    return dummy_data

def make_keywordlist(user_id):
    users = create_user_keyword()
    u_cols = ['user_id', 'keyword', 'weight']
    users_df = pd.DataFrame(users, columns=u_cols)

    selected_rows = users_df[users_df['user_id'] == user_id]
    keyword_weight_dict = selected_rows.set_index('keyword')['weight'].to_dict()

    # print(users_df)
    return keyword_weight_dict