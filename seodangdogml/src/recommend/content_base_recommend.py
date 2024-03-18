from pydantic import BaseModel
from fastapi import APIRouter
router = APIRouter()


class Item(BaseModel):
    message: str


@router.post("/recommend_news")
def process_data(item: Item):
    # item 객체를 이용하여 요청 데이터 처리
    print(item.message)
    return {"message": "Data processed successfully!"}
