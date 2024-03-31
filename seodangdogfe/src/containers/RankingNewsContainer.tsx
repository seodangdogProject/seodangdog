"use client";
import { useEffect, useRef, useState } from "react";
import styled from "./RankingNewsContainer.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "@/utils/http-commons";
import Loading from "../hoc/loading";
import { useRouter } from "next/navigation";
import changeDateFormat from "@/utils/changeDateFormat";
export default function RankingNewsContainer() {
  const cx = classNames.bind(styled);
  const [newsList, setNewsList] = useState<any[]>([]);
  const [category, setCategory] = useState<string>("most-view");
  const mostViewEl = useRef<HTMLDivElement>(null);
  const mostSolvedEl = useRef<HTMLDivElement>(null);
  const router = useRouter();
  useEffect(() => {
    (async () => {
      const res = await privateFetch("/main/" + category, "GET");
      if (res.status !== 200) {
        // 오류처리
        router.replace("/");
      }
      const data = (await res.json())[0].newsPreviewList;
      setNewsList(data);
    })();
  }, [category]);

  // method
  function toggle(inputCategory: string): void {
    if (inputCategory === category) return;
    setCategory(inputCategory);
    mostViewEl.current?.classList.toggle(cx("active"));
    mostSolvedEl.current?.classList.toggle(cx("active"));
  }
  function goNewsDetail(path: string) {
    router.push("/news/" + path);
  }
  if (newsList.length === 0) {
    <Loading />;
  } else {
    return (
      <>
        <div className={styled.container}>
          <div className={styled.toggle__container}>
            <div className={cx("toggle")}>
              <div
                ref={mostViewEl}
                onClick={() => toggle("most-view")}
                className={cx("toggle__item", "active")}
              >
                많이 본 뉴스
              </div>
              <div
                ref={mostSolvedEl}
                onClick={() => toggle("most-solved")}
                className={cx("toggle__item")}
              >
                많이 푼 뉴스
              </div>
            </div>
          </div>
          <div className={cx("section", ["box-shodow-custom"])}>
            {/* top3 섹션 START */}
            <div className={cx("top3")}>
              <div
                onClick={() => goNewsDetail(newsList[0].newsSeq)}
                className={cx("first")}
              >
                <div className={cx("first__card")}>
                  <img src={newsList[0].newsImgUrl} alt="" />
                  <div className={cx("first__card__content")}>
                    <div className={cx("first__card__content__title")}>
                      {newsList[0].newsTitle}
                    </div>
                    <div className={cx("first__card__content__date")}>
                      {changeDateFormat(newsList[0].newsCreatedAt)}
                    </div>
                  </div>
                </div>
              </div>
              <div className={cx("second-third-container")}>
                <div
                  onClick={() => goNewsDetail(newsList[1].newsSeq)}
                  className={cx("second")}
                >
                  <img src={newsList[1].newsImgUrl} alt="" />
                  <div className={cx("info")}>
                    <div className={cx("title")}>{newsList[1].newsTitle}</div>
                    <div className={cx("description")}>
                      {newsList[1].newsDescription}
                    </div>
                    <div className={cx("date")}>
                      {changeDateFormat(newsList[1].newsCreatedAt)}
                    </div>
                  </div>
                </div>
                <div
                  onClick={() => goNewsDetail(newsList[2].newsSeq)}
                  className={cx("third")}
                >
                  <img src={newsList[2].newsImgUrl} alt="" />
                  <div className={cx("info")}>
                    <div className={cx("title")}>{newsList[2].newsTitle}</div>
                    <div className={cx("description")}>
                      {newsList[2].newsDescription}
                    </div>
                    <div className={cx("date")}>
                      {changeDateFormat(newsList[2].newsCreatedAt)}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            {/* top3 섹션 END */}
            <ul className={cx("other")}>
              {newsList.map((item, index) => {
                if (index > 2) {
                  return (
                    <li
                      onClick={() => goNewsDetail(item.newsSeq)}
                      key={item.newsSeq}
                      className={cx("other__item")}
                    >
                      <img src={item.newsImgUrl} alt="" />
                      <div className={cx("info")}>
                        <div className={cx("title")}>{item.newsTitle}</div>
                        <div className={cx("description")}>
                          {item.newsDescription}
                        </div>
                        <div className={cx("date")}>
                          {changeDateFormat(item.newsCreatedAt)}
                        </div>
                      </div>
                    </li>
                  );
                }
              })}
            </ul>
          </div>
        </div>
      </>
    );
  }
}
