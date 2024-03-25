"use client";
import { useEffect, useState } from "react";
import styled from "./RankingNewsContainer.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "@/utils/http-commons";
import Loading from "../hoc/loading";
import { useRouter } from "next/navigation";
export default function RankingNewsContainer() {
  const cx = classNames.bind(styled);
  const [newsList, setNewsList] = useState<any[]>([]);
  const [category, setCategory] = useState<string>("most-view");
  const router = useRouter();
  useEffect(() => {
    const token = localStorage.getItem("accessToken") || "";
    (async () => {
      const res = await privateFetch("/main/" + category, token, "GET");
      if (res.status !== 200) {
        // 오류처리
        router.replace("/landing");
      }
      const data = await res.json();
      console.log(data[0].newsPreviewList);
      setNewsList(data[0].newsPreviewList);
    })();
  }, []);
  if (newsList.length === 0) {
    <Loading />;
  } else {
    return (
      <>
        <div className={styled.container}>
          <div className={styled.toggle__container}>
            <div className={cx("toggle")}>
              <div className={cx("toggle__item", "active")}>많이 본 뉴스</div>
              <div className={cx("toggle__item")}>많이 푼 뉴스</div>
            </div>
          </div>
          <div className={cx("section", ["box-shodow-custom"])}>
            {/* top3 섹션 START */}
            <div className={cx("top3")}>
              <div className={cx("first")}>
                <img src={newsList[0].newsImgUrl} alt="" />
              </div>
              <div className={cx("second-third-container")}>
                <div className={cx("second")}>
                  <img src={newsList[1].newsImgUrl} alt="" />
                  <div className={cx("info")}>
                    <div className={cx("title")}>
                      [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
                    </div>
                    <div className={cx("description")}>
                      NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                    </div>
                    <div className={cx("date")}>2024.03.06. 오전 11:37</div>
                  </div>
                </div>
                <div className={cx("third")}>
                  <img src={newsList[2].newsImgUrl} alt="" />
                  <div className={cx("info")}>
                    <div className={cx("title")}>
                      [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
                    </div>
                    <div className={cx("description")}>
                      NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                    </div>
                    <div className={cx("date")}>2024.03.06. 오전 11:37</div>
                  </div>
                </div>
              </div>
            </div>
            {/* top3 섹션 END */}
            <ul className={cx("other")}>
              {newsList.map((item, index) => {
                if (index > 2) {
                  return (
                    <li key={index} className={cx("other__item")}>
                      <img src={item.newsImgUrl} alt="" />
                      <div className={cx("info")}>
                        <div className={cx("title")}>{item.newsTitle}</div>
                        <div className={cx("description")}>
                          {item.newsDescription}
                        </div>
                        <div className={cx("date")}>{item.newsCreatedAt}</div>
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
