# FastAPI 구동법
> 참고 : https://wikidocs.net/175214

## fastAPI 서버 구동법

### 1. 필요 라이브러리 설치
- ```seodangdogml```에서 ```pip install -r requirements.txt```를 입력하여 설치한다.

### 2. fastAPI 서버 구동
- ```seodangdogml/src```로 이동하여, ```uvicorn main:app --reload --port=5000``` 를 입력하여 서버를 구동합니다. 그러면 5000번 포트에서 서버가 구동됩니다.
- ```main:app```에서 ```main```은 ```main.py``` 파일을 의미하고 ```app```은 ```main.py```의 ```app``` 객체를 의미합니다. ```--reload``` 옵션은 프로그램이 변경되면 서버 재시작 없이 그 내용을 반영하라는 의미입니다.

## ML 개발자 참고 사항
### 1. 로컬의 빌드 환경을 ```requirements.txt```로 만드는 법
 - ```pip list --format=freeze > requirements.txt```