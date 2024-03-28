"use client";
import Cover from "@/components/newsDetail/Cover";
import NewsContent from "@/components/newsDetail/NewsContent";
import Quiz from "@/components/newsDetail/Quiz";
import { privateFetch } from "@/utils/http-commons";
import classNames from "classnames/bind";
import { usePathname } from "next/navigation";
import { useEffect, useState } from "react";
import styled from "./NewsDetailContainer.module.css";
export default function NewsDetailContainer() {
  const [currentQuestion, setCurrentQuestion] = useState<number>(0);
  const cx = classNames.bind(styled);

  const [data, setData] = useState({});
  const [keywords, setKeywords] = useState<any[]>([]);
  const pathname = usePathname();

  useEffect(() => {
    // 데이터 받아오는 함수 START
    (async () => {
      const res = await privateFetch("/news/" + pathname.split("/")[2], "GET");
      if (res.status === 200) {
        let resData = await res.json();
        let keywordList = [];
        console.log(typeof Object.entries(resData.newsKeyword));
        for (const [key] of Object.entries(resData.newsKeyword)) {
          keywordList.push(key);
        }
        setKeywords(keywordList);
        setData(resData);
      } else {
        console.log("error 발생");
      }
    })();
  }, []);
  return (
    <>
      <div className={cx("container")}>
        <div className={cx("detail-container", ["box-shodow-custom"])}>
          <NewsContent keywords={keywords} data={data} />
          {currentQuestion === 0 ? (
            <Cover goNext={setCurrentQuestion} />
          ) : (
            <Quiz />
          )}
        </div>
      </div>
    </>
  );
}
