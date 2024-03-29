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
    # print(response)
    while True:
        try:
            response = client.chat.completions.create(
                model=MODEL,
                messages=[
                    # {"role": "system", "content": prompt + json_format},
                    {"role": "user",
                     "content": prompt + "\n\n" + "뉴스 기사 : " + json_format + "\n\n" + "뉴스 기사 : " + USER_INPUT_MSG}
                ],
                temperature=0,
                # response_format 지정하기
                response_format={"type": "json_object"}
            )
            return json.loads(response.choices[0].message.content)
        except Exception as e:
            print(f"GPT 요청 실패 : {e}")

def question_generate(news):

    prompt_base = "문제는 어려운 난이도야. 반드시 한국어로 답해줘. 문제와 정답, 정답의 해석을 다음 json 포맷으로 응답해줘.\n\n"

    prompt_word = "기사에서 나온 어려운 어휘 중에서 사전에 등재된 어휘의 뜻을 맞추는 사지선다 문제를 만들어줘. question_text에는 문제의 질문이 들어가. choices에는 선택지가 들어가. " + prompt_base
    prompt_judge = "기사의 내용으로 사지선다 문제를 만들어줘. 네 개의 선택지 중 하나는 뉴스 기사의 내용과 맞지 않아." + prompt_base
    prompt_churon = "아래 기사를 읽고 4명이 대화를 하는데 한 명이 틀린 사실을 말하고 있는 사람을 고르는 문제를 만들어줘 난이도는 어렵게. json에서 question_example의 형식은 아래 형식을 갖추되 내용은 아래 뉴스 기사의 내용으로 말해줘. 응답은 아래 json 포맷으로 응답해줘."

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
    json_format_churon = '''json 포맷 : 
{
	"question": {
	    "question_text" : "다음 중 사실과 거리가 먼 말을 한 사람을 고르시오.",
	    "conversation" : {},
	    "choices": {
	    }
	},
	"answer": {
	    "person",
	    "reason"
	}
}'''
    news_text = news["newsTitle"] + "\n\n" + news["newsMainText"]

    news_quiz = []
    news_quiz.append(gpt_req(news_text, prompt_word, json_format_word))
    news_quiz.append(gpt_req(news_text, prompt_judge, json_format_judge))
    news_quiz.append(gpt_req(news_text, prompt_churon, json_format_churon))

    news["newsQuiz"] = news_quiz

    return news