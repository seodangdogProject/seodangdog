# jdk 17 사용
FROM openjdk:17-jdk

# jar파일 위치가 build/libs/*.jar
COPY build/libs/*.jar app.jar

# 실행하기
ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar","app.jar"]
