"use client";

import { Dispatch, SetStateAction, useRef } from "react";
import Cover from "@/components/newsDetail/Cover";
import NewsContent from "@/components/newsDetail/NewsContent";
import Quiz from "@/components/newsDetail/Quiz";
import { privateFetch } from "@/utils/http-commons";
import classNames from "classnames/bind";
import { usePathname } from "next/navigation";
import { useEffect, useState } from "react";
import styled from "./NewsDetailContainer.module.css";
import Summary from "@/components/newsDetail/Summary";
import NotSolved from "@/components/newsDetail/NotSolved";
export default function NewsDetailContainer() {
  // react전용 변수
  const pathname = usePathname();
  const cx = classNames.bind(styled);

  //퀴즈 관련 변수
  const [currentQuestion, setCurrentQuestion] = useState<number>(0);
  const [data, setData] = useState(null);
  const [isSolved, setIsSolved] = useState<boolean>(false);
  const [keywords, setKeywords] = useState<any[]>([]);
  const [quiz_1, setQuiz_1] = useState<any[]>([]);
  const [quiz_2, setQuiz_2] = useState<any[]>([]);
  const [quiz_3, setQuiz_3] = useState<any[]>([]);
  const [quizData, setQuizData] = useState<any[]>([]);

  const [currentQuizNumber, setCurrentQuizNumber] = useState<number>(0); // 0 : 커버페이지 , 1,2,3 : 퀴즈

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
        console.log(resData);
        setIsSolved(resData.solved);
        setKeywords(keywordList);
        setData(resData);
        setQuizData(resData.newsQuiz);
      } else {
        console.log("error 발생");
      }
    })();
    // 데이터 받아오는 함수 END
  }, []);

  return (
    <>
      {isSolved ? (
        // 풀었으면 렌더링
        <>풀었다</>
      ) : (
        // 풀지 않았으면 렌더링
        <NotSolved
          data={data}
          currentQuizNumber={currentQuizNumber}
          setCurrentQuizNumber={setCurrentQuizNumber}
          keywords={keywords}
          quizData={quizData}
          cx={cx}
        />
      )}
    </>
  );
}
