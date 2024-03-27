#Part 1. 모듈 가져오기
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
from datetime import datetime
from dateutil.relativedelta import relativedelta
from dateutil.parser import parse
from time import sleep
import os
import time
import json

def time_calculator(days):
    result = []
    now = datetime.now()

    for days_before in range(days + 1):
        before_date = now - relativedelta(days=days_before)
        # print("{}일 전 :".format(days_before), before_date)

        before_date_str = before_date.strftime('%Y%m%d')
        # print(before_date_str)
        result.append(before_date_str)
    return result

def tag_decomposer(content):
    # `span` 태그 제거
    spans = content.select("span.end_photo_org")
    for span in spans:
        span.decompose()
    # 'strong' 태그 제거
    strongs = content.select("strong")
    for strong in strongs:
        strong.decompose()
    # 'div' 태그 제거
    divs = content.select("div")
    for div in divs:
        div.decompose()
    # 'figure' 태그 제거
    figures = content.select("figure")
    for figure in figures:
        figure.decompose()
    # br을 줄바꿈 문자로 변경
    for br in content.find_all("br"):
        br.replace_with("\n")
    return content

def headline_objector(headline_html):
    headline = {}
    headline["title"] = headline_html.select("a")[-1].text.strip()
    try:
        headline["photo"] = headline_html.select_one("img")["src"]
    except:
        headline["photo"] = None
    headline["lede"] = headline_html.select_one("span.lede").text.strip()
    headline["pressName"] = headline_html.select_one("span.writing").text.strip()
    headline["url"] = headline_html.select("a")[-1]["href"]

    # print(headline)
    return headline

def news_objector(driver, headline_html, options):
    try:
        news_url = headline_html.select("a")[-1]["href"]
        # news_driver = webdriver.Chrome(options=options)
        news_driver = driver
        news_driver.get(news_url)
        news_driver.find_element(By.CLASS_NAME,'_SUMMARY_BTN').click()
        for i in range(10):
            if news_driver.find_element(By.CLASS_NAME, '_SUMMARY_CONTENT_BODY').text != "":
                break
            sleep(0.5)
        news_driver.find_element(By.ID, 'ct')

        news_html = news_driver.page_source  # 현재 페이지의 전체 소스코드 추출
        news_soup = BeautifulSoup(news_html, 'html.parser')

        news = {}
        # 제목 가져오기
        news["newsTitle"] = news_soup.select_one('#title_area > span').text
        # AI 요약문 가져오기
        ai_content = news_soup.select_one("._SUMMARY_CONTENT_BODY")
        if (ai_content.find('strong') != None):
            ai_content.find('strong').decompose()
        news["newsSummary"] = ai_content.decode_contents().replace("<br/>", "\n").strip().split("\n\n")

        if(news["newsSummary"] == "" or news["newsSummary"] == None):
            return None
        # 발행일시 가져오기
        # dateStr : 헤드라인 크롤러와 형식을 맞춰야함, 일시는 따로 빼기
        news["newsCreatedAt"] = news_soup.select_one("span.media_end_head_info_datestamp_time")["data-date-time"]
        # 기자 이름 가져오기
        news["newsReporter"] = news_soup.select_one("span.byline_s").text[:3]
        # 첫번째 사진 가져오기
        news["newsImgUrl"] = None
        photo_html = news_soup.select_one(".end_photo_org img")
        if(photo_html != None):
            news["newsImgUrl"] = photo_html["src"]
        # 본문 가져오기
        content = news_soup.select_one("#dic_area")
        content = tag_decomposer(content)
        news["newsMainText"] = content.text.strip()
        news["news_url"] = news_url

        return news
    except:
        return None

def file_save(news):
    # json 파일로 저장
    with open('resource/news.json', 'w', encoding="utf8") as f:
        json.dump(news, f, indent=4, ensure_ascii=False)
    # print("==> 현재까지의 작업 내용을 저장하였습니다.")
    # print("==> 현재까지 크롤링한 뉴스 수 : ", news_count)

def crawling_main():
    #Part 2. 셀레니움 크롬창 제어
    options = webdriver.ChromeOptions()
    options.add_argument("headless")
    options.add_experimental_option('excludeSwitches', ['enable-logging'])
    # options.add_argument('start-maximized')	# 크롬 최대화
    # options.add_experimental_option("detach", True) # 크롤링 중인 창 닫지 않고 유지

    base_url = "https://news.naver.com"

    main_driver = webdriver.Chrome(options=options)
    main_driver.get(base_url+"/main/officeList.naver")
    main_driver.find_element(By.ID, 'groupOfficeList')


    #Part 3. BeautifulSoup4로 소스코드 추출
    main_html = main_driver.page_source # 현재 페이지의 전체 소스코드 추출
    main_soup = BeautifulSoup(main_html, 'html.parser')


    #Part 4. 반복문 활용 언론사별 뉴스 추출
    news = []
    news_count = 0
    # with open('../resource/news.json','r', encoding='utf8') as f:
    #     news = json.load(f)
    #     news_count = len(news)

    press_list = main_soup.select('ul.group_list a ')

    # 언론사 선택 (종합지만 선택. 경향신문~한국일보)
    press_list = press_list[:10]

    # 뉴스 크롤링 일(日) 수 계산
    date_list = time_calculator(0) # 90 : 3개월

    # 서버 로그 기록을 위한 print
    print(f"{date_list[0]} 크롤링 시작.")

    for index, press in enumerate(press_list, 0):
        press_url = base_url+press["href"]
        press_id = press["href"][-3:]
        press_name = press.text
        # print('{} 번째 언론사 이름: {}, 링크 : {}'.format(index, press.text, press_url))

        # print("==== {} 크롤링 시작".format(press.text))

        # news.append({"press_id" : press_id,
        #              "press_name" : press_name,
        #              "contents" : {}
        #              })

        # 드라이버 설정
        # press_driver = webdriver.Chrome(options=options)
        press_driver = main_driver
        for date in date_list:
            # print("====> ", date)

            page_count = 1
            page_num = 1
            page_limit = 1

            # for page_num in range(1,page_count+1):
            while page_num <= page_count and page_num <= page_limit:
                # 크롤링할 페이지 주소 설정
                press_driver.get(press_url + "&date=" + date + "&page=" + str(page_num))
                press_driver.find_element(By.CLASS_NAME, 'content')  # 파싱할 부분 선택

                press_html = press_driver.page_source  # 현재 페이지의 전체 소스코드 추출
                press_soup = BeautifulSoup(press_html, 'html.parser')

                # 페이지 수 세기 (현재 페이지는 strong 태그로 묶여있으므로 +1을 해야 전체 페이지 수 카운트)
                if(page_num == 1):
                    page_count = len(press_soup.select('div.paging > a')) + 1
                    # print(page_count)

                headline_html_list = press_soup.select('ul.type06_headline > li')
                for headline_html in headline_html_list:
                    news_returned = news_objector(main_driver, headline_html, options)
                    if(news_returned != None):
                        news_returned["media"] = {}
                        news_returned["media"]["mediaCode"] = press_id
                        news_returned["media"]["mediaName"] = press_name
                        news.append(news_returned)
                        news_count += 1
                    # else:
                        # print("----------- 실패! url :", headline_html.select("a")[-1]["href"])

                headline_html_list = press_soup.select('ul.type06 > li')
                for headline_html in headline_html_list:
                    news_returned = news_objector(main_driver, headline_html, options)
                    if (news_returned != None):
                        news_returned["media"] = {}
                        news_returned["media"]["mediaCode"] = press_id
                        news_returned["media"]["mediaName"] = press_name
                        news.append(news_returned)
                        news_count += 1
                    # else:
                        # print("----------- 실패! url :", headline_html.select("a")[-1]["href"])

                page_num += 1


        # print("==> {} 크롤링 종료".format(press.text))
        file_save(news)

    print(f"{date_list[0]} 크롤링 종료. 크롤링한 뉴스 수 : {news_count}")
    main_driver.quit()

def test():
    from selenium.webdriver.chrome.service import Service
    service = Service(executable_path="resource/chromedriver")
    # service = Service(executable_path="resource\\chromedriver.exe")
    options = webdriver.ChromeOptions()
    # options.add_experimental_option("detach", True) # 크롤링 중인 창 닫지 않고 유지
    # options.add_argument("headless")
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-dev-shm-usage')
    main_driver = webdriver.Chrome(service=service, options=options)
    # main_driver = webdriver.Chrome(options=options)
    print(main_driver)
    main_driver.get("https://www.naver.com/")
    print(main_driver)
    main_driver.quit()
    print("selenium test closed.")