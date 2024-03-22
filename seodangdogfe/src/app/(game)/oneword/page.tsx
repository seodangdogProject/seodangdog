"use client";
// WordGame.tsx
import React, { useState, useEffect, useCallback, useRef } from "react";
import { useRecoilState, RecoilRoot } from "recoil";
import { gameWordListState, Item } from "../../../atoms/wordGame";
import styles from "./oneword_layout.module.css";
import Lottie from "lottie-react";
import TimerIcon from "../../../assets/timer-icon.svg";
import ProgressBar from "@ramonak/react-progress-bar";
import GameIcon from "../../../assets/quiz-logo-icon.svg";

const OneWord: React.FC = () => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [sec, setSec] = useState<number>(0);
    const [wordList] = useRecoilState(gameWordListState); // wordList
    const [inputValues, setInputValues] = useState<string[]>([]); //
    const inputRefs = useRef<Array<HTMLInputElement | null>>([]); // 사용자 input 박스 체크
    const [timerId, setTimerId] = useState<NodeJS.Timeout | null>(null); // 타이머 식별자 체크하기
    const [answerSec, setAnswerSec] = useState<number>(0); // 타이머 2의 초

    // 현재 문제의 정답을 가져오는 함수
    const getCurrentAnswer = () => {
        return wordList[currentIndex]?.answer || "";
    };

    const setAnswerLength = () => {
        setInputValues(
            Array.from({ length: getCurrentAnswer().length }, () => "")
        );
    };

    // 각 입력 요소에 대한 변경 핸들러 (하나 치면 다음으로 자동 넘어가는거)
    const handleInputChange = (index: number, value: string) => {
        const newInputValues = [...inputValues];
        newInputValues[index] = value;

        // 만약 입력된 문자열이 최대 길이에 도달하면 다음 input 요소로 포커스 이동
        if (value.length === 1 && index < inputValues.length - 1) {
            inputRefs.current[index + 1]?.focus();
        }

        setInputValues(newInputValues);
    };

    // 입력 처리 함수
    const handleKeyPress = (
        index: number,
        event: React.KeyboardEvent<HTMLInputElement>
    ) => {
        if (event.key === "Enter") {
            console.log("검사하러가기");
            handleConvertWord();
        } else if (
            event.key === "Backspace" &&
            index > 0 &&
            inputValues[index] === ""
        ) {
            // 백스페이스 키 입력 처리
            const newInputValues = [...inputValues];
            newInputValues[index - 1] = ""; // 이전 input 요소에 포커스를 이동하고 값을 지움
            inputRefs.current[index - 1]?.focus();
            setInputValues(newInputValues);
        }
    };

    // 단어 변환 함수
    const handleConvertWord = () => {
        const convertedWord = inputValues.join(""); // 배열에 있는 문자들을 결합하여 단어로 변환
        console.log("Converted word:", convertedWord);

        // 현재 문제의 정답을 가져옵니다.
        const currentAnswer = getCurrentAnswer();

        // 변환된 단어가 현재 문제의 정답과 같은지 확인합니다.
        if (convertedWord === currentAnswer) {
            console.log("정답입니다!");

            // 현재 문제가 마지막 문제가 아니라면 다음 문제로 넘어갑니다.
            if (currentIndex < wordList.length - 1) {
                setCurrentIndex((prevIndex) => prevIndex + 1);
                setSec(0); // 타이머를 0으로 초기화합니다.
            } else {
                // 마지막 문제라면 모든 문제를 완료했음을 알립니다.
                alert("모든 문제를 완료했습니다.");
            }
        }
    };

    const check = useCallback(() => {
        const convertedWord = inputValues.join(""); // 배열에 있는 문자들을 결합하여 단어로 변환
        console.log("Converted word:", convertedWord);

        // 현재 문제의 정답을 가져옵니다.
        const currentAnswer = getCurrentAnswer();

        return convertedWord === currentAnswer;
    }, [inputValues, currentIndex]); // inputValues와 currentIndex에 의존성 추가

    useEffect(() => {
        inputRefs.current[0]?.focus(); // 초기 렌더링 시 첫 번째 input 요소에 포커스 설정
        setAnswerLength(); // 컴포넌트가 렌더링될 때마다 답의 길이에 따라 입력 박스 설정
    }, [currentIndex, wordList]);

    useEffect(() => {
        // 타이머 1
        const timer1 = setInterval(() => {
            console.log("timer 1 재시작");
            if (sec >= 10 && sec < 12) {
                if (!check()) {
                    const currentAnswer = getCurrentAnswer();
                    if (inputValues.join("") !== currentAnswer) {
                        setInputValues(currentAnswer.split(""));
                    }
                } else {
                }
                setSec((prevSec) => prevSec + 1);
            } else if (sec < 12) {
                setSec((prevSec) => prevSec + 1);
            } else if (sec == 12) {
                if (currentIndex == wordList.length - 1) {
                    alert("모든 문제가 끝남");
                    clearInterval(timer1);
                } else {
                    setCurrentIndex((prevIndex) =>
                        prevIndex < wordList.length - 1
                            ? prevIndex + 1
                            : prevIndex
                    );
                    setSec(0);
                }
            }
        }, 1000);

        return () => {
            clearInterval(timer1);
        };
    }, [sec]);

    // useEffect(() => {
    //     // 타이머 1
    //     const timer1 = setInterval(() => {
    //         console.log("timer 1 재시작");
    //         if (sec >= 5) {
    //             console.log("timer 1 중단");
    //             clearInterval(timer1);
    //             // 타이머 1이 종료되고 타이머 2 시작
    //             if (!check()) {
    //                 console.log("timer2 재시작");
    //                 // 틀리면
    //                 // timer2의 초는 0부터
    //                 setAnswerSec(0);
    //                 // 현재 답 보여주기
    //                 const currentAnswer = getCurrentAnswer();
    //                 if (inputValues.join("") !== currentAnswer) {
    //                     setInputValues(currentAnswer.split(""));
    //                 }
    //                 // timer 2 시작
    //                 const timer2 = setInterval(() => {
    //                     addNum();
    //                     if (answerSec >= 5) {
    //                         clearInterval(timer2);
    //                         // 타이머 2가 종료되고 다시 타이머 1 시작
    //                         // 이때 다음 문제로 넘어가야됨
    //                         if (currentIndex == wordList.length - 1) {
    //                             alert("모든 문제가 끝남");
    //                         }

    //                         setCurrentIndex((prevIndex) =>
    //                             prevIndex < wordList.length - 1
    //                                 ? prevIndex + 1
    //                                 : prevIndex
    //                         );
    //                         setSec(0);
    //                         return 0;
    //                     } else {
    //                         addNum();
    //                     }
    //                 }, 1000);
    //             } else {
    //                 if (currentIndex == wordList.length - 1) {
    //                     alert("모든 문제가 끝남");
    //                 } else {
    //                     setCurrentIndex((prevIndex) =>
    //                         prevIndex < wordList.length - 1
    //                             ? prevIndex + 1
    //                             : prevIndex
    //                     );
    //                     setSec(0);
    //                 }
    //             }
    //         } else {
    //             setSec((prevSec) => prevSec + 1);
    //         }
    //     }, 1000);

    //     return () => {
    //         clearInterval(timer1);
    //     };
    // }, [sec, answerSec]);

    return (
        <>
            <div className={styles.container}>
                <div className={styles.content_cotainer}>
                    <div className={styles.header_container}>
                        <GameIcon></GameIcon>
                    </div>
                    <div className={styles.count_container}>
                        <span> {currentIndex + 1} </span>
                        <span> /</span>
                        <span> {wordList.length}</span>
                    </div>

                    <div className={styles.meaning_container}>
                        <div className={styles.language}>한글</div>
                        <div className={styles.meaning_des}>
                            {wordList[currentIndex]?.mean}
                        </div>
                    </div>
                    <div className={styles.answer_conatiner}>
                        {inputValues.map((value, index) => (
                            <div key={index} className={styles.characterBox}>
                                <input
                                    type="text"
                                    maxLength={1}
                                    value={value}
                                    onChange={(e) =>
                                        handleInputChange(index, e.target.value)
                                    }
                                    onKeyDown={(e) => handleKeyPress(index, e)}
                                    ref={(input) => {
                                        inputRefs.current[index] = input;
                                    }}
                                />
                            </div>
                        ))}
                    </div>
                    <div className={styles.timer}>
                        <div className={styles.time_icon}>
                            <TimerIcon></TimerIcon>
                        </div>
                        <div className={styles.progress_wrapper}>
                            <div
                                className={styles.progress_container}
                                style={{
                                    width: `${(sec / 10) * 100}%`,
                                    transition: "width 0.5s ease",
                                }}
                            >
                                <div>{sec}</div>
                                <div>{answerSec}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div></div>
        </>
    );
};

export default OneWord;
