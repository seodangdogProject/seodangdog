"use client";

import { Dispatch, SetStateAction } from "react";
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

  const pathname = usePathname();
  const [data, setData] = useState({});
  const [keywords, setKeywords] = useState<any[]>([]);
  const [quiz_1, setQuiz_1] = useState<any[]>([]);
  const [quiz_2, setQuiz_2] = useState<any[]>([]);
  const [quiz_3, setQuiz_3] = useState<any[]>([]);
  const [quizData, setQuizData] = useState<any[]>([]);
  // 0 : 커버페이지 , 1,2,3 : 퀴즈
  const [currentQuizNumber, setCurrentQuiz] = useState<number>(0);

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
        setQuizData(resData.newsQuiz);
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
          {currentQuizNumber === 0 ? (
            <Cover setCurrentQuiz={setCurrentQuiz} />
          ) : (
            <Quiz
              currentQuizNumber={currentQuizNumber}
              quizData={quizData}
              setCurrentQuiz={setCurrentQuiz}
            />
          )}
        </div>
      </div>
    </>
  );
}
