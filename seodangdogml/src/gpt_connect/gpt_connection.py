import os
import time

from openai import OpenAI
import json
import settings


def gpt_req(news_text, prompt, json_format):

    # 사용 모델을 설정합니다. chat GPT는 gpt-3.5-turbo를 사용합니다.
    MODEL = "gpt-3.5-turbo"
    # USER_INPUT_MSG = '''
    # 아래 기사를 읽고 현우, 철수, 영희, 지훈이 서로 대화를 하는데 틀린 사실을 말하는 한 명을 고르는 문제를 만들어줘. 문제의 난이도는 성인 기준으로도 조금 어려워.\n\n
    # ''' + news_text
    USER_INPUT_MSG = news_text

    request_limit = 100
    for i in range(request_limit):
        try:
            client = OpenAI(api_key = settings.GPT_API_KEY)
            response = client.chat.completions.create(
                model=MODEL,
                messages=[
                    # {"role": "system", "content": prompt + json_format},
                    {"role": "user",
                     "content": prompt + "\n\n" + "json format : \n" + json_format + "\n\n" + "뉴스 기사 : \n" + USER_INPUT_MSG}
                ],
                temperature=0,
                # response_format 지정하기
                response_format={"type": "json_object"}
            )
            question =  json.loads(response.choices[0].message.content)

            # 검증 결과 불합격 시 API 재요청
            if not question_validate(question):
                print("GPT 문제 검증 결과 불합격. 재요청...")
                continue

            question = question_refine(question)

            return question
        except Exception as e:
            print(f"GPT 요청 실패 : {e}")

    raise Exception("GPT 요청에 연속적으로 실패하였습니다.")

def question_generate(news):

    prompt_base = "문제는 어려운 난이도야. 반드시 한국어로 답해줘. 문제와 정답, 정답의 해석을 다음 json 포맷으로 응답해줘.\n\n"

    prompt_word = "기사에서 나온 어휘 중에서 사전에 등재된 어휘의 뜻을 맞추는 사지선다 문제를 만들어줘." + prompt_base
    prompt_judge = "뉴스 기사의 사실들로 사지선다 문제를 만들어줘." + prompt_base
    # prompt_churon = "아래 기사를 읽고 현우, 철수, 영희, 지훈이 대화하고 있어. 이 중 한명이 틀린 사실을 말해. 틀린 사실을 말하는 한 명을 고르는 문제를 만들어줘. 문제는 사지선다 형식이야."
    prompt_churon = "아래 뉴스 기사를 읽고 4명이 대화를 하는데 한 명이 틀린 사실을 말하고 있는 사람을 고르는 문제를 만들어줘. " + prompt_base

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
    news_text = news["newsTitle"] + "\n\n" + news["newsMainText"]

    news_quiz = []
    news_quiz.append(gpt_req(news_text, prompt_word, json_format_word))
    news_quiz.append(gpt_req(news_text, prompt_judge, json_format_judge))
    news_quiz.append(gpt_req(news_text, prompt_churon, json_format_churon))

    news["newsQuiz"] = news_quiz

    return news


def question_validate(question):
    # true/false를 반환
    try:
        # 각 항목들이 존재하는지 확인
        if "question" not in question.keys() \
                or "answer" not in question.keys():
            return False
        else:
            # question value 확인
            if "question_text" not in question["question"].keys() \
                or "choices" not in question["question"].keys():
                return False
            else:
                # 각 key의 value가 빈 문자열이 아님을 검증.
                # question의 질문이 특정 글자 수 이상이어야 함
                if len(question["question"]["question_text"]) == 0:
                    return False
                # key 존재 여부 확인
                if "1" not in question["question"]["choices"].keys() \
                    or "2" not in question["question"]["choices"].keys() \
                    or "3" not in question["question"]["choices"].keys() \
                    or "4" not in question["question"]["choices"].keys():

                        return False
                # 각 선택지들이 빈 문자열이 아님을 검증.
                if len(question["question"]["choices"]["1"]) == 0 \
                        or len(question["question"]["choices"]["2"]) == 0 \
                        or len(question["question"]["choices"]["3"]) == 0 \
                        or len(question["question"]["choices"]["4"]) == 0:
                    return False
            # answer value 확인
            if "number" not in question["answer"].keys() \
                    or "reason" not in question["answer"].keys():
                return False
            else:
                # number와 reason이 빈 문자열이 아님을 검증.
                if len(str(question["answer"]["number"])) == 0 \
                        or len(question["answer"]["reason"]) == 0:
                    return False

        # 각 항목들이 의미하는 바가 올바른지 확인
        # 답이 choices의 key에 해당하는지 확인
        if str(question["answer"]["number"]) not in question["question"]["choices"].keys():
            return False

    except:
        return False

    return True

def question_refine(question):
    # qustion의 정답은 string형이어야 함
    question["answer"]["number"] = str(question["answer"]["number"])

    return question

