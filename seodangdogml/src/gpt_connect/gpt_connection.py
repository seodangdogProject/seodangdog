import os
import time

from openai import OpenAI
import json
import settings


def gpt_req(news_text, prompt, json_format):
    client = OpenAI(api_key = settings.GPT_API_KEY)

    # 사용 모델을 설정합니다. chat GPT는 gpt-3.5-turbo를 사용합니다.
    MODEL = "gpt-3.5-turbo"
    # USER_INPUT_MSG = '''
    # 아래 기사를 읽고 현우, 철수, 영희, 지훈이 서로 대화를 하는데 틀린 사실을 말하는 한 명을 고르는 문제를 만들어줘. 문제의 난이도는 성인 기준으로도 조금 어려워.\n\n
    # ''' + news_text
    USER_INPUT_MSG = news_text

    response = client.chat.completions.create(
        model=MODEL,
        messages=[
            # {"role": "system", "content": prompt + json_format},
            {"role": "user", "content": prompt + json_format + "\n\n" + USER_INPUT_MSG}
        ],
        temperature=0,
        # response_format 지정하기
        response_format = {"type":"json_object"}
    )

    # print(response)
    return response.choices[0].message.content

def question_generate(news_data):

    news_data = [{
  "_id": {
    "$oid": "65fd4763ad8f7197b3696802"
  },
  "newsTitle": "한·중 노선 승객 1년 만에 733% 증가… 항공업 회복 ‘날개’",
  "newsSummary": [
    "한국 항공업 회복을 결정지을 마지막 퍼즐로 불리는 한·중국 노선에 훈풍이 불고 있다.",
    "또 중국인의 한국 단체관광이 재개된 지난해 8월 이후 중국 노선 이용객이 87만명을 넘은 것은 지난달이 처음이다.",
    "코로나19 대유행 여파가 지나간 뒤 지난해 여행 수요가 폭발한 일본과 동남아 등의 노선과 달리 중국 노선은 가장 더디게 회복되는 노선으로 남아있었다."
  ],
  "newsCreatedAt": "2024-02-18 21:53:01",
  "newsReporter": "백소용",
  "newsImgUrl": None,
  "newsMainText": "지난 1월 이용객 87만3329명\n中 단체관광 재개로 수요 늘어\n국제선 총 여객 728만… 57% ↑\n\n한국 항공업 회복을 결정지을 마지막 퍼즐로 불리는 한·중국 노선에 훈풍이 불고 있다. 신종 코로나바이러스 감염증(코로나19) 사태 이후 대거 중단됐던 중국 노선 운항이 서서히 재개되면서 이를 이용한 여객이 올해 들어 8배 넘게 늘었다.\n \n 18일 국토교통부 항공 통계에 따르면 지난달 국제선 여객은 총 728만489명으로, 1년 전보다 57% 증가했다. 코로나19 이전인 2019년 1월과 비교하면 회복률은 91% 수준이다.\n \n 가장 눈에 띄는 것은 중국 노선 여객 증가율이다. 지난달 중국 노선 이용객은 총 87만3329명으로, 지난해 1월(10만4813명)보다 733% 증가했다. 2019년 1월과 비교하면 63% 수준의 회복세다. 또 중국인의 한국 단체관광이 재개된 지난해 8월 이후 중국 노선 이용객이 87만명을 넘은 것은 지난달이 처음이다.\n \n 코로나19 대유행 여파가 지나간 뒤 지난해 여행 수요가 폭발한 일본과 동남아 등의 노선과 달리 중국 노선은 가장 더디게 회복되는 노선으로 남아있었다. 단체관광이 뒤늦게 재개된 영향도 있지만, 중국 경기가 좋지 않고 과거 주류를 이뤘던 단체관광보다 개별관광이 많아졌기 때문이다. 한국인의 중국 관광 선호도도 낮아지는 추세다.\n \n 항공업계는 회복 흐름을 주시하며 중국 노선을 재정비하고 있다. 다만 전면적인 노선 확대보다는 멈췄던 노선을 조금씩 재개하며 여행 수요 회복에 대비하고 있다.\n \n 대한항공은 오는 4월 말부터 한국인들에게 수요가 높은 인천∼장자제·장저우 노선 운항을 재개한다. 제주항공도 올 하계 스케줄을 편성하며 인천∼스자좡 노선을 재운항하기로 결정했다.",
  "news_url": "https://n.news.naver.com/mnews/article/022/0003905302",
  "media": {
    "mediaCode": "022",
    "mediaName": "세계일보"
  },
  "newsPos": [
    {
      "word": "지난",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "1월",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이용",
      "pos": "Noun"
    },
    {
      "word": "객",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "87만",
      "pos": "Number"
    },
    {
      "word": "3329",
      "pos": "Number"
    },
    {
      "word": "명",
      "pos": "Noun"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": "中",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "단체",
      "pos": "Noun"
    },
    {
      "word": "관광",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재개",
      "pos": "Noun"
    },
    {
      "word": "로",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "수요",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "늘어",
      "pos": "Verb"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": "국제선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "총",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "여객",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "728만",
      "pos": "Number"
    },
    {
      "word": "…",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "57%",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "↑",
      "pos": "Foreign"
    },
    {
      "word": "\n\n",
      "pos": "Foreign"
    },
    {
      "word": "한국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "항공",
      "pos": "Noun"
    },
    {
      "word": "업",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "회복",
      "pos": "Noun"
    },
    {
      "word": "을",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "결정",
      "pos": "Noun"
    },
    {
      "word": "지을",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "마지막",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "퍼즐",
      "pos": "Noun"
    },
    {
      "word": "로",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "불리는",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "한",
      "pos": "Verb"
    },
    {
      "word": "·",
      "pos": "Punctuation"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": "에",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "훈풍",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "불",
      "pos": "Noun"
    },
    {
      "word": "고",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "있다",
      "pos": "Adjective"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "신종",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "코로나바이러스",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "감염증",
      "pos": "Noun"
    },
    {
      "word": "(",
      "pos": "Punctuation"
    },
    {
      "word": "코로나",
      "pos": "Noun"
    },
    {
      "word": "19",
      "pos": "Number"
    },
    {
      "word": ")",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "사태",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이후",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "대거",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중단",
      "pos": "Noun"
    },
    {
      "word": "됐던",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "운항",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "서서히",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재개",
      "pos": "Noun"
    },
    {
      "word": "되면서",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이를",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이용",
      "pos": "Noun"
    },
    {
      "word": "한",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "여객",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "올해",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "들어",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "8",
      "pos": "Number"
    },
    {
      "word": "배",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "넘게",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "늘었다",
      "pos": "Verb"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "18일",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "국토교통부",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "항공",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "통계",
      "pos": "Noun"
    },
    {
      "word": "에",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "따르면",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "지난달",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "국제선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "여객",
      "pos": "Noun"
    },
    {
      "word": "은",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "총",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "728만",
      "pos": "Number"
    },
    {
      "word": "489",
      "pos": "Number"
    },
    {
      "word": "명",
      "pos": "Noun"
    },
    {
      "word": "으로",
      "pos": "Josa"
    },
    {
      "word": ",",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "1년",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "전보",
      "pos": "Noun"
    },
    {
      "word": "다",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "57%",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "증가",
      "pos": "Noun"
    },
    {
      "word": "했다",
      "pos": "Verb"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "코로나",
      "pos": "Noun"
    },
    {
      "word": "19",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이전",
      "pos": "Noun"
    },
    {
      "word": "인",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "2019년",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "1월",
      "pos": "Number"
    },
    {
      "word": "과",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "비교",
      "pos": "Noun"
    },
    {
      "word": "하면",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "회복",
      "pos": "Noun"
    },
    {
      "word": "률",
      "pos": "Noun"
    },
    {
      "word": "은",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "91%",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "수준",
      "pos": "Noun"
    },
    {
      "word": "이다",
      "pos": "Josa"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "가장",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "눈",
      "pos": "Noun"
    },
    {
      "word": "에",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "띄는",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "것",
      "pos": "Noun"
    },
    {
      "word": "은",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "여객",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "증가",
      "pos": "Noun"
    },
    {
      "word": "율",
      "pos": "Noun"
    },
    {
      "word": "이다",
      "pos": "Josa"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "지난달",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이용",
      "pos": "Noun"
    },
    {
      "word": "객",
      "pos": "Noun"
    },
    {
      "word": "은",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "총",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "87만",
      "pos": "Number"
    },
    {
      "word": "3329",
      "pos": "Number"
    },
    {
      "word": "명",
      "pos": "Noun"
    },
    {
      "word": "으로",
      "pos": "Josa"
    },
    {
      "word": ",",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "지난해",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "1월",
      "pos": "Number"
    },
    {
      "word": "(",
      "pos": "Punctuation"
    },
    {
      "word": "10만",
      "pos": "Number"
    },
    {
      "word": "4813",
      "pos": "Number"
    },
    {
      "word": "명",
      "pos": "Noun"
    },
    {
      "word": ")",
      "pos": "Punctuation"
    },
    {
      "word": "보다",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "733%",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "증가",
      "pos": "Noun"
    },
    {
      "word": "했다",
      "pos": "Verb"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "2019년",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "1월",
      "pos": "Number"
    },
    {
      "word": "과",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "비교",
      "pos": "Noun"
    },
    {
      "word": "하면",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "63%",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "수준",
      "pos": "Noun"
    },
    {
      "word": "의",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "회복",
      "pos": "Noun"
    },
    {
      "word": "세다",
      "pos": "Verb"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "또",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국인",
      "pos": "Noun"
    },
    {
      "word": "의",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "한국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "단체",
      "pos": "Noun"
    },
    {
      "word": "관광",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재개",
      "pos": "Noun"
    },
    {
      "word": "된",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "지난해",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "8월",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이후",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이용",
      "pos": "Noun"
    },
    {
      "word": "객",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "87만",
      "pos": "Number"
    },
    {
      "word": "명을",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "넘은",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "것",
      "pos": "Noun"
    },
    {
      "word": "은",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "지난달",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "처음",
      "pos": "Noun"
    },
    {
      "word": "이다",
      "pos": "Josa"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "코로나",
      "pos": "Noun"
    },
    {
      "word": "19",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "대유행",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "여파",
      "pos": "Noun"
    },
    {
      "word": "가",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "지나간",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "뒤",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "지난해",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "여행",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "수요",
      "pos": "Noun"
    },
    {
      "word": "가",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "폭발",
      "pos": "Noun"
    },
    {
      "word": "한",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "일본",
      "pos": "Noun"
    },
    {
      "word": "과",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "동남아",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "등",
      "pos": "Noun"
    },
    {
      "word": "의",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": "과",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "달리",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": "은",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "가장",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "더디게",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "회복",
      "pos": "Noun"
    },
    {
      "word": "되는",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": "으로",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "남아있었다",
      "pos": "Verb"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "단체",
      "pos": "Noun"
    },
    {
      "word": "관광",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "뒤늦게",
      "pos": "Adjective"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재개",
      "pos": "Noun"
    },
    {
      "word": "된",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "영향",
      "pos": "Noun"
    },
    {
      "word": "도",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "있지만",
      "pos": "Adjective"
    },
    {
      "word": ",",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "경기",
      "pos": "Noun"
    },
    {
      "word": "가",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "좋지",
      "pos": "Adjective"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "않고",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "과거",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "주류",
      "pos": "Noun"
    },
    {
      "word": "를",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "이뤘던",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "단체",
      "pos": "Noun"
    },
    {
      "word": "관광",
      "pos": "Noun"
    },
    {
      "word": "보다",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "개별",
      "pos": "Noun"
    },
    {
      "word": "관광",
      "pos": "Noun"
    },
    {
      "word": "이",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "많아졌기",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "때문",
      "pos": "Noun"
    },
    {
      "word": "이다",
      "pos": "Josa"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "한국인",
      "pos": "Noun"
    },
    {
      "word": "의",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "관광",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "선호",
      "pos": "Noun"
    },
    {
      "word": "도도",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "낮아지는",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "추세",
      "pos": "Noun"
    },
    {
      "word": "다",
      "pos": "Josa"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "항공",
      "pos": "Noun"
    },
    {
      "word": "업계",
      "pos": "Noun"
    },
    {
      "word": "는",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "회복",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "흐름",
      "pos": "Noun"
    },
    {
      "word": "을",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "주시",
      "pos": "Noun"
    },
    {
      "word": "하며",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "중국",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": "을",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재정",
      "pos": "Noun"
    },
    {
      "word": "비",
      "pos": "Noun"
    },
    {
      "word": "하고",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "있다",
      "pos": "Adjective"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "다만",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "전면",
      "pos": "Noun"
    },
    {
      "word": "적",
      "pos": "Suffix"
    },
    {
      "word": "인",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "확대",
      "pos": "Noun"
    },
    {
      "word": "보다는",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "멈췄던",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": "을",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "조금씩",
      "pos": "Adverb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재개",
      "pos": "Noun"
    },
    {
      "word": "하며",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "여행",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "수요",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "회복",
      "pos": "Noun"
    },
    {
      "word": "에",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "대비",
      "pos": "Noun"
    },
    {
      "word": "하고",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "있다",
      "pos": "Adjective"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "\n",
      "pos": "Foreign"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "대한항공",
      "pos": "Noun"
    },
    {
      "word": "은",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "오는",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "4월",
      "pos": "Number"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "말",
      "pos": "Noun"
    },
    {
      "word": "부터",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "한국인",
      "pos": "Noun"
    },
    {
      "word": "들",
      "pos": "Suffix"
    },
    {
      "word": "에게",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "수요",
      "pos": "Noun"
    },
    {
      "word": "가",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "높은",
      "pos": "Adjective"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "인천",
      "pos": "Noun"
    },
    {
      "word": "∼",
      "pos": "Foreign"
    },
    {
      "word": "장자제",
      "pos": "Noun"
    },
    {
      "word": "·",
      "pos": "Punctuation"
    },
    {
      "word": "장저우",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "운항",
      "pos": "Noun"
    },
    {
      "word": "을",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재개",
      "pos": "Noun"
    },
    {
      "word": "한다",
      "pos": "Verb"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "제주항공",
      "pos": "Noun"
    },
    {
      "word": "도",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "올",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "하",
      "pos": "Exclamation"
    },
    {
      "word": "계",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "스케줄",
      "pos": "Noun"
    },
    {
      "word": "을",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "편성",
      "pos": "Noun"
    },
    {
      "word": "하며",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "인천",
      "pos": "Noun"
    },
    {
      "word": "∼",
      "pos": "Foreign"
    },
    {
      "word": "스자좡",
      "pos": "Noun"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "노선",
      "pos": "Noun"
    },
    {
      "word": "을",
      "pos": "Josa"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "재",
      "pos": "Noun"
    },
    {
      "word": "운항",
      "pos": "Noun"
    },
    {
      "word": "하기로",
      "pos": "Verb"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    },
    {
      "word": "결정",
      "pos": "Noun"
    },
    {
      "word": "했다",
      "pos": "Verb"
    },
    {
      "word": ".",
      "pos": "Punctuation"
    },
    {
      "word": " ",
      "pos": "Whitespace"
    }
  ],
  "newsKeyword": {
    "노선": 0.6089875835916161,
    "중국": 0.2948609231226707,
    "재개": 0.2756999158422357,
    "관광": 0.25447682871231114,
    "회복": 0.2512501012278647,
    "여객": 0.21593263118665829,
    "항공": 0.16864730031396896,
    "운항": 0.15572327034688113,
    "수요": 0.14234290107954478,
    "증가": 0.12244257119655032,
    "국제선": 0.12030756074922604,
    "단체": 0.11995041026280122,
    "코로나": 0.11435458121536762,
    "이용": 0.10894663819033909,
    "여행": 0.08275106638975736,
    "한국인": 0.08173170025597061,
    "스자좡": 0.07521745308793024,
    "장저우": 0.07521745308793024,
    "장자제": 0.07521745308793024,
    "지난달": 0.07348834753588597
  },
  "newsSummaryKeyword": {
    "노선": 0.6637068785491732,
    "회복": 0.3166805940214036,
    "중국": 0.25448638444965,
    "항공": 0.2299484755576527,
    "대유행": 0.15740873329873142
  }
}]

    prompt_base = "문제는 어려운 난이도야. 반드시 한국어로 답해줘. 문제와 정답, 정답의 해석을 다음 json 포맷으로 응답해줘.\n\n"

    prompt_word = "기사에서 나온 어려운 어휘 중에서 사전에 등재된 어휘의 뜻을 맞추는 사지선다 문제를 만들어줘. " + prompt_base
    prompt_judge = "기사의 내용으로 사지선다 문제를 만들어줘. 네 개의 선택지 중 하나는 뉴스 기사의 내용과 맞지 않아." + prompt_base
    prompt_churon = "아래 기사를 읽고 현우, 철수, 영희, 지훈이 대화하고 있어. 이 중 한명이 틀린 사실을 말해. 틀린 사실을 말하는 한 명을 고르는 문제를 만들어줘. 문제는 사지선다 형식이야." + prompt_base

    json_format_word = '''{
    "question": {
        "question_text",
        "choices": {
            "1",
            "2",
            "3",
            "4",
        }
    },
    "answer": {
        "number",
        "reason"
    }
}
    '''
    json_format_judge = '''{
    "question": {
        "question_text",
        "choices": {
            "1",
            "2",
            "3",
            "4",
        }
    },
    "answer": {
        "number",
        "reason"
    }
}'''
    json_format_churon = '''{
            "question": {
                "question_text" : "다음 중 사실과 거리가 먼 말을 한 사람을 고르시오.",
                "choices": {
                    "현우",
                    "철수",
                    "영희",
                    "지훈",
                }
            },
            "answer": {
                "person",
                "reason"
            }
        }'''

    question_list = []
    # news_data = findNews(10)

    for news in news_data:
        news_text = news["newsTitle"] + "\n\n" + news["newsMainText"]

        news_quiz = []
        s_t = time.time()
        news_quiz.append(json.loads(gpt_req(news_text, prompt_word, json_format_word)))
        e_t = time.time()
        print("종료. 소요 시간 : ", e_t-s_t)
        news_quiz.append(json.loads(gpt_req(news_text, prompt_judge, json_format_judge)))
        news_quiz.append(json.loads(gpt_req(news_text, prompt_churon, json_format_churon)))

        news["newsQuiz"] = news_quiz
        question_list.append(news_quiz)

    with open('question_list.json', 'w', encoding="utf8") as f:
        json.dump(question_list, f, indent=4, ensure_ascii=False)

    return news_data

# print("시작")
# question_generate([])
# print("종료")
# print("종료. 소요 시간 : ", e_t-s_t)