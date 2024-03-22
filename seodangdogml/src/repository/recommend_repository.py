from fastapi import FastAPI

router = FastAPI()


@router.get('/fast/recommend_repository')
def recommend_repository():
    print("fast_recommend_repository")
# MySQL 서버에 연결하는 함수
# def connect_to_mysql():
#     connection = mysql.connector.connect(
#         host="localhost",  # MySQL 호스트 이름 또는 IP 주소
#         user="username",   # MySQL 사용자 이름
#         password="password",   # MySQL 비밀번호
#         database="database_name"   # 사용할 데이터베이스 이름
#     )
#     return connection
#
# # 사용자 정보를 가져오는 엔드포인트
# @app.get("/users/{user_id}")
# async def get_user(user_id: int):
#     connection = connect_to_mysql()
#     cursor = connection.cursor()
#
#     query = "SELECT * FROM users WHERE id = %s"
#     cursor.execute(query, (user_id,))
#     result = cursor.fetchone()
#
#     cursor.close()
#     connection.close()
#
#     return {"user": result}
