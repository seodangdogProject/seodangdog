"use client";
import { useEffect, useRef, useState } from "react";
import styled from "./RecommendNewsContainer.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "../utils/http-commons";
import { useRouter } from "next/navigation";
import changeDateFormat from "@/utils/changeDateFormat";
export default function RecommendNewsContainer() {
  const cx = classNames.bind(styled);

  const [category, setCategory] = useState<string>("user-recommend/v2");
  const [newsList, setNewsList] = useState<any[]>([]);
  const userRecommendEl = useRef<HTMLDivElement>(null);
  const otherRecommendEl = useRef<HTMLDivElement>(null);

  const router = useRouter();

  // method
  function toggle(inputCategory: string): void {
    if (category === inputCategory) return;
    setCategory((prev) => inputCategory);
    userRecommendEl.current?.classList.toggle(cx("active"));
    otherRecommendEl.current?.classList.toggle(cx("active"));
  }

  useEffect(() => {
    (async () => {
      try {
        const res = await privateFetch("/main/" + category, "GET");
        let data = await res.json();
        console.log(data);
        if (data instanceof Array) {
          data = data[0].newsPreviewList;
        } else {
          data = data.newsPreviewList;
        }
        let subArrays = [];
        for (let i = 0; i < data.length; i += 3) {
          subArrays.push(data.slice(i, i + 3));
        }
        setNewsList(subArrays);
      } catch (error) {
        console.error(error);
      }
    })();
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
          <ul className={cx("line")}>
            {newsList.map((subGroup, index) => (
              <li key={index} className={cx("item-container")}>
                {subGroup.map((item: any, idx: number) => (
                  <div
                    onClick={() => router.push("/news/" + item.newsSeq)}
                    key={item.newsSeq}
                    className={cx("line-item")}
                  >
                    <img src={item.newsImgUrl} alt="" />
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
          </ul>
        </div>
      </div>
    </>
  );
}
