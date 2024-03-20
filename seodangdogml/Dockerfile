FROM python:3.11-slim
# fastAPI 의 작업 위치가 /code이라는 뜻
WORKDIR /code

# 현재 디렉토리의 모든 파일들을 컨테이너의 /app 디렉토리에 복사한다.
COPY ./requirements.txt /code/requirements.txt

# pip install 해주기 
RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt
# main.py 위치가 ./fastapi_server 
COPY ./src /code

# 실행하기 app 에 fastapi_server를 붙여넣었으므로 app으로 이동해서 uvicorn main:app --reload
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "5000"]
