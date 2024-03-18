from faker import Faker
import random
import pandas as pd


def create():
    fake = Faker()

    # 경제와 관련된 키워드 리스트

    economic_keywords = [
        '경제', '금융', '증권', '무역', '산업', '부동산', '투자', '자본', '경영', '수출',
        '소비', '임금', '인플레이션', '경기', '빈곤', '채권', '주식', '가계', '통화',
        '소득', '지수', '산업화', '자본주의', '금리', '유가', '규제', '무역관계', '불황',
        '경쟁력', '산업혁명', '외환', '투자은행', '재정정책', '환율', '소비자물가',
        '주가지수', '주식시장', '신용등급', '대외무역', '금융시장', '국내총생산', '자본금',
        '유동성', '이자율', '세계경제', '미국경제', '중국경제', '유럽경제', '아시아경제',
        '국내외 경제', '코스피', '코스닥', '투자심리', '신용카드', '부동산시장', '무역적자',
        '무역수지', '원자재', '취업률', '실업률', '금융위기', '전업체', '포괄적 거래',
        '주택시장', '전세계', '고용시장', '경제사회', '소득분배', '경제학', '국제금융',
        '부동산거래', '부동산가격', '금리인하', '금리인상', '중소기업', '유동성위기',
        '세계무역기구', '경제정책', '통화정책', '경기진작', '세계금융위기', '외환위기',
        '경제성장률', '경제활동인구', '경제정의', '세계경제포럼', '세계은행', '기획재정부',
        '국제통화기금', '원자재시장', '수요예측', '재경부', '경제전망', '가계부채',
        '산업활동인구', '수출입', '고용안정', '인플레이션율', '임금체계', '일자리창출',
        '가계소비', '국내총수요', '자본수출', '무역저감', '전세계적', '중소기업체',
        '해외진출', '해외투자', '무역통상', '부채총량', '소비자심리지수', '경제성장률',
        '외국인투자', '자본수입', '국제경제', '투자처', '소비재', '통화량', '재무제표',
        '소비자물가지수', '부가가치', '무역수요', '무역규제', '자본금융', '금리재정책',
        '통화량조절', '통화량감축', '국내총생산량', '지방세', '소득세', '종합소득세',
        '지방경제', '국제금융시장', '국제금융기구', '국제금융기금', '국제금융권',
        '국제금융거래', '국제금융센터', '국제금융산업', '국제금융회사', '국제금융소속',
        '국제금융단체', '국제금융전문', '국제금융기업', '국제금융인력', '국제금융전문가',
        '국제금융계', '국제금융전문인력', '국제금융전문가들', '국제금융인재',
        '국제금융인재풀', '국제금융인재모집', '국제금융인재채용', '국제금융인재공고']
        # 더미 데이터 생성
    dummy_data = []

    for _ in range(1000):  # 총 1000개의 더미 데이터 생성
        user_id = fake.random_number(digits=2)  # 랜덤한 유저 아이디 생성
        keyword = random.choice(economic_keywords)  # 경제와 관련된 랜덤한 키워드 선택
        weight = random.randint(1, 30)  # 가중치는 1부터 30 사이의 랜덤 값

        # ID와 키워드가 모두 중복되는 경우에 가중치 증가
        for index, data in enumerate(dummy_data):
            if user_id == data[0] and keyword == data[1]:
                dummy_data[index] = (user_id, keyword, data[2] + 3)
                break
        else:
            dummy_data.append((user_id, keyword, weight))

    # for data in enumerate(dummy_data):
    #     if data[1][0] == 50:
    #         print(data[1][1], data[1][2])


    df = pd.DataFrame(dummy_data, columns=['ID', 'Keyword', 'Weight'])

    return df

def make_keywordlist(user_id):
    df = create()
    selected_rows = df[df['ID'] == user_id]
    keyword_weight_dict = selected_rows.set_index('Keyword')['Weight'].to_dict()

    # print("make_keywordlist", keyword_weight_dict)
    return keyword_weight_dict

# make_keywordlist(50)