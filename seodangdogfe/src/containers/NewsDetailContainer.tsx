"use client";
import NewsContent from "@/components/newsDetail/NewsContent";
import styled from "./NewsDetailContainer.module.css";
import classNames from "classnames/bind";
import Quiz from "@/components/newsDetail/Quiz";
import Cover from "@/components/newsDetail/Cover";
import { useEffect, useState } from "react";
import { privateFetch } from "@/utils/http-commons";
export default function NewsDetailContainer() {
  const [currentQuestion, setCurrentQuestion] = useState<number>(0);
  const cx = classNames.bind(styled);

  const [data, setData] = useState({});

  useEffect(() => {
    // 데이터 받아오는 함수 START
    (async () => {
      const res = await privateFetch("/news/16964", "GET");
      if (res.status === 200) setData(await res.json());
      else {
        console.log("error 발생");
      }
    })();
  }, []);
  return (
    <>
      <div className={cx("container")}>
        <NewsContent data={data} />
        {currentQuestion === 0 ? (
          <Cover goNext={setCurrentQuestion} />
        ) : (
          <Quiz />
        )}
      </div>
    </>
  );
}
