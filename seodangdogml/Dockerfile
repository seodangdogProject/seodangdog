FROM python:3.11-slim
# fastAPI 의 작업 위치가 /code이라는 뜻
WORKDIR /code

# 현재 디렉토리의 모든 파일들을 컨테이너의 /app 디렉토리에 복사한다.
COPY ./requirements.txt /code/requirements.txt

# 찬영 : 크롬을 설치한다.
RUN apt-get update
RUN apt-get -y install wget
RUN mkdir /code/resource
RUN wget --no-verbose -O /code/resource/chrome.deb https://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_114.0.5735.90-1_amd64.deb
RUN apt-get install -y /code/resource/chrome.deb
# 찬영 : 형태소 분리를 위한 jdk를 설치한다.
RUN apt install default-jdk -y

# pip install 해주기 
RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt
# main.py 위치가 ./fastapi_server 
COPY ./src /code

# 실행하기 app 에 fastapi_server를 붙여넣었으므로 app으로 이동해서 uvicorn main:app --reload
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "5000"]
