import re
import string
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from konlpy.tag import Okt

import os
import sys
sys.path.append(os.path.abspath('src'))
from mongo import *

import time


# 정규화
def reg_process(text_origin):
    text_origin=text_origin.strip()
    text_origin=re.compile('<.*?>').sub('', text_origin)
    text_origin = re.compile('[%s]' % re.escape(string.punctuation)).sub(' ', text_origin)
    text_origin = re.sub('\s+', ' ', text_origin)
    text_origin = re.sub(r'\[[0-9]*\]', ' ', text_origin)
    text_origin=re.sub(r'[^\w\s]', ' ', str(text_origin).strip())
    text_origin = re.sub(r'\d', ' ', text_origin)
    text_origin = re.sub(r'\s+', ' ', text_origin)
    return text_origin


# 명사/영단어 추출, 한글자 제외, 불용어 제거
def extract_noun(text):
    # 불용어 파일에서 목록 읽기
    with open('preprocessing/stopwords.txt', 'r', encoding='utf8') as f:
        list_file = f.readlines()
    stopwords = list_file[0].split(",")

    okt = Okt()
    n = []
    word = okt.nouns(text)
    p = okt.pos(text)
    for pos in p:
      if pos[1] in ['SL']:
        word.append(pos[0])
    for w in word:
      if len(w)>1 and w not in stopwords:
        n.append(w)
    return " ".join(n)

def preprocess(text):
  return extract_noun(reg_process(text))

def keyword_generate(news_data, targetCol, resultCol):
    # 뉴스 가져오기
    # news_list = findNews(200)
    # news_list = getNewsAll()
    # 뉴스를 말뭉치로 변환
    corpus = []

    print("전처리 작업 시작...")
    cnt_for_logging = 0
    for news in news_data:
        # 제목과 본문을 합치기
        if targetCol == "newsSummary":
            summary_text = ""
            for summary in news[targetCol]:
                summary_text += summary
            corpus.append(preprocess(news['newsTitle'] + summary_text))
        else:
            corpus.append(preprocess(news['newsTitle'] + news[targetCol]))
        cnt_for_logging += 1
        if cnt_for_logging % 500 == 0:
            print("===> ", cnt_for_logging, "개 뉴스 전처리 완료")

    print("TF-IDF 벡터화 시작...")
    df = pd.DataFrame({'id': range(1, len(corpus) + 1), 'content': corpus}, index=None)

    tfidf_vectorizer = TfidfVectorizer()
    data = df['content']
    news_vectors = tfidf_vectorizer.fit_transform(data)

    # 단어 목록 가져오기
    feature_names = tfidf_vectorizer.get_feature_names_out()

    # 단어 목록을 데이터프레임으로 변환
    word_df = pd.DataFrame(news_vectors.toarray(), columns=feature_names)

    # 기존 데이터프레임에 단어 목록을 추가
    df = pd.concat([df, word_df], axis=1)
    df = df.drop(columns=['content'])

    # 뉴스 데이터에 키워드 저장
    print("뉴스 데이터에 키워드 저장...")
    for i in range(len(news_data)):
        if targetCol == "newsSummary":
            for keyword_cnt in range(1,6):
                # news_data[i][resultCol] = list(df.loc[i].sort_values(ascending=False)[1:6].index)
                news_data[i][resultCol] = {df.loc[i].sort_values(ascending=False).index[keyword_cnt] : df.loc[i].sort_values(ascending=False).iloc[keyword_cnt]}
        else:
            # news_data[i][resultCol] = list(df.loc[i].sort_values(ascending=False)[1:21].index)
            for keyword_cnt in range(1,21):
                news_data[i][resultCol] = {df.loc[i].sort_values(ascending=False).index[keyword_cnt] : df.loc[i].sort_values(ascending=False).iloc[keyword_cnt]}

    return news_data