"use client";
import { Suspense, useEffect, useRef, useState } from "react";
import styled from "./RecommendNewsContainer.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "../utils/http-commons";
import { useRouter } from "next/navigation";
import changeDateFormat from "@/utils/changeDateFormat";
import { RefreshReq, KeywordInfo, NewsData } from "@/atoms/type";
import RefreshIcon from "@/assets/Refresh-icon.svg";
import { SourceTextModule } from "vm";
import { useRecoilState, useSetRecoilState } from "recoil";
import { loadingState } from "@/atoms/loadingRecoil";
import Loading from "@/app/loading1";

export default function RecommendNewsContainer() {
  const cx = classNames.bind(styled);
  const [category, setCategory] = useState<string>("user-recommend/v2");
  const [newsData, setNewsData] = useState<NewsData[]>([]);
  const [newsList, setNewsList] = useState<any[]>([]);
  const [reqList, setReqList] = useState<RefreshReq[]>([]);
  const userRecommendEl = useRef<HTMLDivElement>(null);
  const otherRecommendEl = useRef<HTMLDivElement>(null);

  const router = useRouter();

  // recoil 변수
  const [isLoading] = useRecoilState(loadingState);
  const setLoadingState = useSetRecoilState(loadingState);

  // method
  function toggle(inputCategory: string): void {
    if (category === inputCategory) return;
    setCategory((prev) => inputCategory);
    userRecommendEl.current?.classList.toggle(cx("active"));
    otherRecommendEl.current?.classList.toggle(cx("active"));
  }

  const refresh = () => {
    refreshKeyword()
      .then(() => {
        console.log("업데이트 요청");
        return reMainRef();
      })
      .then(() => {
        console.log("재추천");
        // 두 요청이 모두 완료된 후에 할 일
      })
      .then(() => {
        setReqList([]);
      })
      .catch((error) => {
        console.error("Error:", error);
        // 에러 처리
      });
  };

  const refreshKeyword = () => {
    return (async () => {
      try {
        console.log("newsData :", newsData);
        if (newsData != undefined) {
          console.log("변환");
          const updatedReqList = newsData.map((item, idx) =>
            convertToDTO(item)
          );
          setReqList(updatedReqList);
          console.log("updatedReqList : ", updatedReqList);
        }

        const res = await privateFetch("/keyword/refre", "POST", reqList);
        let data = await res.json();
        console.log(data);
      } catch (error) {
        console.error(error);
      }
    })();
  };

  const reMainRef = () => {
    return (async () => {
      try {
        const res = await privateFetch("/main/" + category, "GET");
        let data = await res.json();
        if (data instanceof Array) {
          data = data[0].newsPreviewList;
          setNewsData(data);
        } else {
          data = data.newsPreviewList;
          setNewsData(data);
        }
        let subArrays = [];
        for (let i = 0; i < data.length; i += 3) {
          subArrays.push(data.slice(i, i + 3));
        }

        setNewsList(subArrays);
        setLoadingState(false);
        console.log(subArrays);

        if (newsData) {
          const updatedReqList = newsData.map((item, idx) =>
            convertToDTO(item)
          );
          setReqList(updatedReqList);
        }
      } catch (error) {
        console.error(error);
      }
    })();
  };

  function convertToDTO(obj: NewsData): RefreshReq {
    const newsSeq = obj.newsSeq;
    const keywordInfoList: KeywordInfo[] = [];

    for (const [keyword, weight] of Object.entries(obj.newsKeyword)) {
      keywordInfoList.push({ keyword, weight });
    }

    return {
      newsSeq,
      keywordInfoList,
    };
  }

  useEffect(() => {
    setLoadingState(true);
    reMainRef();
  }, [category]);

  return (
    <>
      <div className={styled.container}>
        <div className={styled.toggle__container}>
          <div className={cx("toggle")}>
            <div
              onClick={() => toggle("user-recommend/v2")}
              ref={userRecommendEl}
              className={cx("toggle__item", "active")}
            >
              내 취향 뉴스
            </div>
            <div
              onClick={() => toggle("other-recommend")}
              ref={otherRecommendEl}
              className={cx("toggle__item")}
            >
              다른 사람 뉴스
            </div>
          </div>
        </div>
        <div className={cx("section", ["box-shodow-custom"])}>
          {isLoading ? (
            <Loading />
          ) : (
            <>
              <ul className={cx("line")}>
                {newsList.map((subGroup, index) => (
                  <li key={index} className={cx("item-container")}>
                    {subGroup.map((item: any, idx: number) => (
                      <div
                        onClick={() => router.push("/news/" + item.newsSeq)}
                        key={item.newsSeq}
                        className={cx("line-item")}
                      >
                        <img
                          src={
                            item.newsImgUrl == "None"
                              ? "/images/default-news-image.jpg"
                              : item.newsImgUrl
                          }
                          alt=""
                        />
                        <div className={cx("title")}>{item.newsTitle}</div>
                        <div className={cx("description")}>
                          {item.newsDescription}
                        </div>
                        <div className={cx("date")}>
                          조회수 {item.countView} •{" "}
                          {changeDateFormat(item.newsCreatedAt)}
                        </div>
                      </div>
                    ))}
                  </li>
                ))}
                {category === "user-recommend/v2" && (
                  <div className={cx("refresh-container")}>
                    <div
                      className={styled.refreshButton}
                      onClick={() => refresh()}
                    >
                      <RefreshIcon className={styled.icon} />
                    </div>
                  </div>
                )}
              </ul>
            </>
          )}
        </div>
      </div>
    </>
  );
}
