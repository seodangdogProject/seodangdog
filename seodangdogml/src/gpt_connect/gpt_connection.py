import os
from openai import OpenAI
from repository.news_repository import findNews
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
            {"role": "system", "content": prompt + json_format},
            {"role": "user", "content": USER_INPUT_MSG}
        ],
        temperature=0,
        # response_format 지정하기
        response_format = {"type":"json_object"}
    )

    # print(response)
    return response.choices[0].message.content

def question_generate(news_data):

    prompt_base = "문제는 어려운 난이도야. 문제와 정답, 정답의 해석을 다음 json 포맷으로 응답해줘.\n\n"

    prompt_word = "기사에서 나온 어려운 어휘 중에서 사전에 등재된 어휘의 뜻을 맞추는 사지선다 문제를 만들어줘. " + prompt_base
    prompt_judge = "기사의 내용으로 사지선다 문제를 만들어줘. 네 개의 선택지 중 하나는 뉴스 기사의 내용과 맞지 않아." + prompt_base
    prompt_churon = "아래 기사를 읽고 현우, 철수, 영희, 지훈이 서로 대화를 하는데 틀린 사실을 말하는 한 명을 고르는 문제를 만들어줘." + prompt_base

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
    news_data = findNews(10)

    for news in news_data:
        news_text = news["newsTitle"] + "\n\n" + news["newsMainText"]

        news_quiz = []
        news_quiz.append(json.loads(gpt_req(news_text, prompt_word, json_format_word)))
        news_quiz.append(json.loads(gpt_req(news_text, prompt_judge, json_format_judge)))
        news_quiz.append(json.loads(gpt_req(news_text, prompt_churon, json_format_churon)))

        news["newsQuiz"] = news_quiz
        question_list.append(news_quiz)

    with open('question_list.json', 'w', encoding="utf8") as f:
        json.dump(question_list, f, indent=4, ensure_ascii=False)

    return news_data