"use client";
// WordGame.tsx
import React, { useState, useEffect, useCallback } from "react";
import { useRecoilState, RecoilRoot } from "recoil";
import { gameWordListState, Item } from "../../../atoms/wordGame";
import styles from "./oneword_layout.module.css";
import Lottie from "lottie-react";
import TimerIcon from "../../../assets/timer-icon.svg";
import ProgressBar from "@ramonak/react-progress-bar";
import GameIcon from "../../../assets/quiz-logo-icon.svg";

const OneWord: React.FC = () => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [sec, setSec] = useState(0);
    const [wordList, setWordList] = useRecoilState(gameWordListState);
    const [testWord, setTestWord] = useState("김아림특화");
    const [inputValues, setInputValues] = useState(
        Array(testWord.length).fill("")
    ); // 입력된 문자열을 추적하는 상태
    // const [inputValues, setInputValues] = useState(
    //     Array(wordList[currentIndex].answer.length).fill('')
    // ); // 입력된 문자열을 추적하는 상태

    // 각 입력 요소에 대한 변경 핸들러
    const handleChange = (index: number, value: string) => {
        const newInputValues = [...inputValues]; // 이전 입력 값을 복사
        newInputValues[index] = value; // 변경된 값으로 업데이트
        setInputValues(newInputValues); // 변경된 입력 값을 상태에 저장
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter") {
            console.log("검사하러가기");
            handleConvertWord();
        }
    };

    // 단어 변환 함수
    const handleConvertWord = () => {
        const convertedWord = inputValues.join(""); // 배열에 있는 문자들을 결합하여 단어로 변환
        console.log("Converted word:", convertedWord);
    };

    useEffect(() => {
        const interval_id = setInterval(() => {
            setSec((sec) => {
                const newcount = sec + 1;
                console.log(newcount);
                if (newcount == 11) {
                    console.log("정지");
                    clearInterval(interval_id);
                    //alert('종료');
                }
                return newcount;
            });
        }, 1000);

        // 이부분 뒤에 수정하기 (자동으로 넘기도록 -> 타이머 없애고 조건되면 자동으로 바뀌면서 타이머 재시동)
        const interval = setInterval(() => {
            setCurrentIndex((prevIndex) => {
                if (prevIndex + 1 >= wordList.length) {
                    clearInterval(interval); // 배열의 끝에 도달하면 interval을 멈춥니다.
                }
                return prevIndex + 1;
            });
        }, 5000);

        return () => {
            clearInterval(interval_id);
            clearInterval(interval);
        };
    }, [wordList.length]);

    return (
        <>
            <div className={styles.container}>
                <div className={styles.content_cotainer}>
                    <div className={styles.header_container}>
                        <GameIcon></GameIcon>
                    </div>
                    <div className={styles.count_container}>
                        <span> 1</span>
                        <span> /</span>
                        <span> 10</span>
                    </div>

                    <div className={styles.meaning_container}>
                        <div className={styles.language}>한글</div>
                        <div className={styles.meaning_des}>
                            조선 왕조가 자신들의 역사를 편찬한 사서
                        </div>
                    </div>
                    <div className={styles.answer_conatiner}>
                        {testWord.split("").map((char, index) => (
                            <div key={index} className={styles.characterBox}>
                                <input
                                    type="text"
                                    maxLength={1}
                                    value={inputValues[index]}
                                    onChange={(e) =>
                                        handleChange(index, e.target.value)
                                    }
                                    onKeyDown={handleKeyDown}
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
                                {/* <div>{sec}</div> */}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                {/* <h1>{wordList[currentIndex].idx}</h1>
                <h1>{wordList[currentIndex].mean}</h1>
                <h1>{wordList[currentIndex].answer}</h1> */}
                {/* {wordList[currentIndex].answer.split('').map((char, index) => (
                    <div key={index} className={styles.characterBox}>
                        <input
                            type="text"
                            maxLength={1}
                            value={inputValues[index]}
                            onChange={(e) =>
                                handleChange(index, e.target.value)
                            }
                        />
                    </div>
                ))} */}
                <button
                    onClick={() =>
                        setCurrentIndex((prevIndex) => prevIndex + 1)
                    }
                >
                    다음 단어
                </button>
            </div>
        </>
    );
};

export default OneWord;
