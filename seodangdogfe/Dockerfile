# 가져올 이미지를 정의
FROM node:20.11.1 as builder
# 경로 설정하기
RUN mkdir /usr/src/app
WORKDIR /usr/src/app
# package.json 워킹 디렉토리에 복사 (.은 설정한 워킹 디렉토리를 뜻함)
COPY package.json ./

# 명령어 실행 (의존성 설치)
RUN npm install

# 호스트 머신의 현재 디렉토리 파일들을 컨테이너 내부로 전부 복사
COPY ./ ./

# npm build
RUN npm run build

# ---------------- 
# nginx 
FROM nginx:stable-alpine
# 기본 nginx 설정 파일을 삭제한다. (custom 설정과 충돌 방지)

COPY --from=builder /usr/src/app/build/standalone/build/server/app /usr/share/nginx/html


RUN rm /etc/nginx/conf.d/default.conf
# nignx 위치에 넣어주기

# custom 설정파일을 컨테이너 내부로 복사한다.
COPY nginx/nginx.conf /etc/nginx/conf.d

# 컨테이너의 80번 포트를 열어준다.
EXPOSE 80

# nginx 서버를 실행하고 백그라운드로 동작하도록 한다.
CMD ["nginx", "-g", "daemon off;"]
