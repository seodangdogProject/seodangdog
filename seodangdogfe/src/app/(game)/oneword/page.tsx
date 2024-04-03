"use client";
// WordGame.tsx
import React, { useState, useEffect, useCallback, useRef } from "react";
import { useRecoilState, RecoilRoot, useSetRecoilState } from "recoil";
import { useRouter } from "next/navigation";

import {
  gameWordListState,
  correctWordListState,
  unCorrectWordListState,
  GameItem,
} from "../../../atoms/wordGame";
import styles from "./oneword_layout.module.css";
import TimerIcon from "../../../assets/timer-icon.svg";
import GameIcon from "../../../assets/quiz-logo-icon.svg";
import CorrectIcon from "../../../assets/correct-icon.svg";
import UncorrectIcon from "../../../assets/uncorrect-icon.svg";

const OneWord: React.FC = () => {
  const router = useRouter();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [sec, setSec] = useState<number>(0);
  const [wordList] = useRecoilState(gameWordListState); // wordList
  const [correctWordList] = useRecoilState(correctWordListState); // wordList
  const [unCorrectWordList] = useRecoilState(unCorrectWordListState); // wordList
  const [inputValues, setInputValues] = useState<string[]>([]); //
  const inputRefs = useRef<Array<HTMLInputElement | null>>([]); // 사용자 input 박스 체크
  const [answerSec, setAnswerSec] = useState<number>(0); // 타이머 2의 초
  const [isAnswer, setIsAnswer] = useState<boolean>(false);
  const [isUnCorrect, setIsUnCorrect] = useState<boolean>(false);
  const [isCorrectIcon, setIsCorrectIcon] = useState<boolean>(false);
  const [isUnCorrectIcon, setIsUnCorrectIcon] = useState<boolean>(false);
  const setCorrectWordList = useSetRecoilState(correctWordListState);
  const setUnCorrectWordList = useSetRecoilState(unCorrectWordListState);

  // 단어장에 추가하기
  const addItemToList = useCallback(
    (flag: boolean, itemToAdd: GameItem): void => {
      if (flag) {
        setCorrectWordList([...correctWordList, itemToAdd]);
      } else {
        setUnCorrectWordList([...unCorrectWordList, itemToAdd]);
      }
    },
    [
      setCorrectWordList,
      setUnCorrectWordList,
      correctWordList,
      unCorrectWordList,
    ]
  );

  // 현재 문제의 정답을 가져오는 함수
  const getCurrentAnswer = () => {
    return wordList[currentIndex]?.word || "";
  };

  const setAnswerLength = () => {
    setInputValues(Array.from({ length: getCurrentAnswer().length }, () => ""));
  };

  // 각 입력 요소에 대한 변경 핸들러 (하나 치면 다음으로 자동 넘어가는거)
  const handleInputChange = (index: number, value: string) => {
    const newInputValues = [...inputValues];
    newInputValues[index] = value;
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
    } else if (
      event.key == " " &&
      index >= 0 &&
      index < inputValues.length - 1
    ) {
      event.preventDefault();
      inputRefs.current[index + 1]?.focus();
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

      setIsCorrectIcon(true);
      setTimeout(() => {
        setIsCorrectIcon(false);
      }, 300);
      addItemToList(true, wordList[currentIndex]);
      console.log(correctWordList);
      // 현재 문제가 마지막 문제가 아니라면 다음 문제로 넘어갑니다.
      if (currentIndex < wordList.length - 1) {
        setCurrentIndex((prevIndex) => prevIndex + 1);
        setSec(0); // 타이머를 0으로 초기화합니다.
      } else {
        // 마지막 문제라면 모든 문제를 완료했음을 알립니다.
        // alert("모든 문제를 완료했습니다.");
        router.push("/game_result");
      }
    } else {
      setIsUnCorrect(true);
      console.log("shaking");

      // 0.3초 후에 흔들림 효과를 제거
      setTimeout(() => {
        setIsUnCorrect(false);
      }, 300);
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
      if (sec >= 10 && sec <= 11) {
        setIsUnCorrectIcon(true);
        setTimeout(() => {
          setIsUnCorrectIcon(false);
        }, 300);

        setSec((prevSec) => prevSec + 1);
      } else if (sec >= 10 && sec < 13) {
        // 답 보여주기 부분
        if (!check()) {
          addItemToList(false, wordList[currentIndex]);
          console.log(wordList[currentIndex]);
          console.log("unCorrectWordList : ", unCorrectWordList);
          const currentAnswer = getCurrentAnswer();
          if (inputValues.join("") !== currentAnswer) {
            setInputValues(currentAnswer.split(""));
          }
          setIsAnswer(true);
        } else {
        }
        setSec((prevSec) => prevSec + 1);
      } else if (sec < 13) {
        setSec((prevSec) => prevSec + 1);
      } else if (sec == 13) {
        setIsAnswer(false);
        if (currentIndex == wordList.length - 1) {
          alert("모든 문제가 끝남");
          clearInterval(timer1);
          router.push("/game_result");
        } else {
          setCurrentIndex((prevIndex) =>
            prevIndex < wordList.length - 1 ? prevIndex + 1 : prevIndex
          );
          setSec(0);
        }
      }
    }, 1000);

    return () => {
      clearInterval(timer1);
    };
  }, [sec]);

  return (
    <>
      <div className={styles.container}>
        <div className={styles.content_cotainer}>
          {isCorrectIcon && (
            <CorrectIcon
              style={{
                position: "fixed",
                top: "50%",
                left: "50%",
                marginLeft: "5%",
                transform: "translate(-50%, -50%)",
              }}
            ></CorrectIcon>
          )}
          {isUnCorrectIcon && (
            <UncorrectIcon
              style={{
                position: "fixed",
                top: "50%",
                left: "50%",
                marginLeft: "5%",
                transform: "translate(-50%, -50%)",
              }}
            ></UncorrectIcon>
          )}

          <div className={styles.header_container}>
            <GameIcon></GameIcon>
          </div>
          <div className={styles.count_container}>
            <span> {currentIndex + 1} </span>
            <span> /</span>
            <span> {wordList.length}</span>
          </div>
          <div className={styles.language}>한글</div>
          <div className={styles.meaning_container}>
            <div
              className={styles.meaning_des}
              style={{
                fontSize: (() => {
                  if (wordList[currentIndex]?.mean.length < 12) {
                    return "40px";
                  } else if (wordList[currentIndex]?.mean.length < 24) {
                    return "30px";
                  } else {
                    return "20px";
                  }
                })(),
              }}
            >
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
                  className={` ${isUnCorrect ? styles.shaking : ""}`}
                  onChange={(e) => handleInputChange(index, e.target.value)}
                  onKeyDown={(e) => handleKeyPress(index, e)}
                  ref={(input) => {
                    inputRefs.current[index] = input;
                  }}
                  style={{
                    color: isAnswer ? "red" : "black",
                  }}
                  disabled={isAnswer}
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
                {/* <div>{sec}</div>
                                <div>{answerSec}</div> */}
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
