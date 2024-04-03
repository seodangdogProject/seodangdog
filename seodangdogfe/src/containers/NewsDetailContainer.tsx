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
import Solved from "@/components/newsDetail/Solved";
import { useRecoilState, useSetRecoilState } from "recoil";
import { loadingState } from "@/atoms/loadingRecoil";
import Loading from "@/app/loading1";
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

    // 유저가 작성한 답 변수들
    const [answerList, setAnswerList] = useState<number[]>([]);
    const [userSummary, setUserSummary] = useState<string>("");
    const [correctList, setCorrectList] = useState<boolean[]>([]);

    const [currentQuizNumber, setCurrentQuizNumber] = useState<number>(0); // 0 : 커버페이지 , 1,2,3 : 퀴즈

    //
    // recoil 변수
    const [isLoading] = useRecoilState(loadingState);
    const setLoadingState = useSetRecoilState(loadingState);

    useEffect(() => {
        setLoadingState(true);
        console.log(12345);
        // 데이터 받아오는 함수 START
        (async () => {
            console.log(pathname.split("/")[3]);
            const res = await privateFetch(
                "/news/" +
                    pathname.split("/")[2] +
                    "/" +
                    pathname.split("/")[3],
                "GET"
            );

            if (res.status === 200) {
                let resData = await res.json();
                let keywordList = [];
                for (const [key] of Object.entries(resData.newsKeyword)) {
                    keywordList.push(key);
                }
                console.log("받아온 데이터 : ", resData);
                setIsSolved(resData.solved);
                console.log("useAnswerList : ", resData.userAnswerList);
                if (
                    resData.userAnswerList === null ||
                    resData.userAnswerList.length === 0
                ) {
                    setAnswerList(Array(3).fill(5));
                } else {
                    setAnswerList(resData.userAnswerList);
                }
                setKeywords(keywordList);
                setData(resData);
                setQuizData(resData.newsQuiz);
                setLoadingState(false);
            } else {
                console.log("error 발생");
            }
        })();
        // 데이터 받아오는 함수 END
    }, [isSolved]);
    useEffect(() => {
        if (isSolved) {
            setCurrentQuizNumber(1);
            console.log(quizData);
        }
    }, [isSolved]);
    // METHOD
    // 제출 버튼 누르면 서버로 데이터 보냄
    async function solveQuiz() {
        const body = {
            newsSeq: pathname.split("/")[2],
            userAnswerList: answerList,
            correctList: correctList,
            userSummary: {
                userSummaryContent: userSummary,
                userSummaryKeyword: [],
            },
        };
        try {
            const res = await privateFetch("/news/solve", "PATCH", body);
            if (res.status !== 200) {
                throw "서버통신 에러";
            } else {
                setIsSolved(true);
            }
        } catch (error) {
            console.error(error);
        }
    }
    return (
        <>
            {data && (
                <>
                    {isSolved ? (
                        // 풀었으면 렌더링
                        <Solved
                            data={data}
                            keywords={keywords}
                            newsSeq={Number(pathname.split("/")[2])}
                            cx={cx}
                            answerList={answerList}
                            currentQuizNumber={currentQuizNumber}
                            setCurrentQuizNumber={setCurrentQuizNumber}
                            setUserSummary={setUserSummary}
                            quizData={quizData}
                        />
                    ) : (
                        // 풀지 않았으면 렌더링
                        <NotSolved
                            data={data}
                            currentQuizNumber={currentQuizNumber}
                            setCurrentQuizNumber={setCurrentQuizNumber}
                            keywords={keywords}
                            quizData={quizData}
                            cx={cx}
                            newsSeq={Number(pathname.split("/")[2])}
                            setAnswerList={setAnswerList}
                            answerList={answerList}
                            setUserSummary={setUserSummary}
                            solveQuiz={solveQuiz}
                            setCorrectList={setCorrectList}
                        />
                    )}
                </>
            )}
        </>
    );
}
