# GPT
GPT_API_KEY = "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"

# S3
S3_BUCKET_NAME = "seodangdog-s3"
S3_LOCATION = "ap-northeast-2"
S3_ACCESS_KEY = "AKIAYS2NT7VYZUUQXEU6"
S3_SECRET_KEY = "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"

# wordcloud font path
WC_FONT_PATH = "fast_resources/NanumGothicExtraBold.ttf"

# mongoDB
MONGO = {
    "host" : "seodangdog",
    "port" : "27017",
    "username" : "dogsoedang",
    "password" : "sssdangorg56",
}
MONGO_URI = f"mongodb://{MONGO['username']}:{MONGO['password']}@j10e104.p.ssafy.io:{MONGO['port']}/{MONGO['host']}?authSource=admin",
MONDGO_DBNAME = 'seodangdog'

# mysql
MYSQL = {
    "host" : 'seodangdog-mysql.cza82kskeqwa.ap-northeast-2.rds.amazonaws.com',
    "port" : 3306,
    "user" : 'seodangdog',
    "passwd" : 'dogseodang0311',
    "db":'seodangdog',
    "charset":'utf8'
}

