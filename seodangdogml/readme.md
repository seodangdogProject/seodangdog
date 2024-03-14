# FastAPI 구동법
> 참고 : https://wikidocs.net/175214

### 디렉토리 구성
- fastapi_env : fastApi 서버가 구동되기 위한 가상환경이 담겨 있습니다. 또한 각종 라이브러리가 저장되어있는 곳입니다.
- fastAPI_server : fastApi 서버가 저장된 곳입니다. 파이썬으로 작업할 공간입니다.

## fastAPI 서버 구동법
### 1. 가상환경 접속
- 콘솔에서 ```fastapi_env/Scripts```로 이동 후, ```activate```를 입력합니다. 콘솔 입력 텍스트 앞에 ```(fastapi_env)```라고 나오면 성공입니다.
    - 예시 : ```(fastapi_env) C:\fastapi_env\Scripts>```
- 나중에 가상환경에서 빠져나오고 싶으면 ```deactivate```를 입력합니다.

### 2. 필요 라이브러리 설치
- ```fastapi_env/Scripts```에서 ```pip install -r requirements.txt```를 입력하여 설치한다.
- 안되면 찬영에게 말한다.

### 3. fastAPI 서버 구동
- ```fastAPI_server``` 로 이동 후, ```uvicorn main:app --reload``` 를 입력하여 서버를 구동합니다. 그러면 기본적으로는 8000번 포트에서 서버가 구동됩니다.
- ```main:app```에서 ```main```은 ```main.py``` 파일을 의미하고 ```app```은 ```main.py```의 ```app``` 객체를 의미합니다. ```--reload``` 옵션은 프로그램이 변경되면 서버 재시작 없이 그 내용을 반영하라는 의미입니다.

