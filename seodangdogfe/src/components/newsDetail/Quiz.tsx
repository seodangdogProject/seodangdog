import { Dispatch, SetStateAction, useEffect, useState } from "react";
import styled from "./Quiz.module.css";
import classNames from "classnames/bind";
import Timer from "./Timer";
import CorrectIcon from "@/assets/correct-icon.svg";
import UncorrectIcon from "@/assets/uncorrect-icon.svg";
interface Props {
  setCurrentQuizNumber: Dispatch<SetStateAction<number>>;
  quizData: any[];
  currentQuizNumber: number;
  setAnswerList: Dispatch<SetStateAction<number[]>>;
  answerList: number[];
  setCorrectList: Dispatch<SetStateAction<boolean[]>>;
}
export default function Quiz({
  setCurrentQuizNumber,
  quizData,
  currentQuizNumber,
  setAnswerList,
  answerList,
  setCorrectList,
}: Props) {
  const cx = classNames.bind(styled);
  const [selectedAnswer, setSelectedAnswer] = useState(5);
  const [isCorrect, setIsCorrect] = useState(false);
  const [isUnCorrect, setIsUnCorrect] = useState(false);

  useEffect(() => {
    console.log("후후후", answerList);
    const timer = setTimeout(() => {
      // console.log("맞았나? : ", checkAnswer());
      // moveToNextQuiz();
      checkAnswer();
      setTimeout(() => {
        moveToNextQuiz();
      }, 500);
    }, 15000);
    return () => {
      clearTimeout(timer);
    };
  }, [answerList]);

  useEffect(() => {
    setIsCorrect(false);
    setIsUnCorrect(false);
  }, [currentQuizNumber]);

  // METHOD
  // 정답 고르면 실행 되는 함수
  function selectAnswer(ans: number) {
    setSelectedAnswer(ans);
    setAnswerList((prev) => {
      let newArr = prev.slice();
      newArr[currentQuizNumber - 1] = ans;
      console.log(newArr);
      return newArr;
    });
  }

  function moveToNextQuiz() {
    // checkAnswer();
    // setCorrectList((prev) => {
    //   let newArr = prev.slice();
    //   newArr[currentQuizNumber - 1] = checkAnswer();
    //   return newArr;
    // });
    // console.log(checkAnswer())
    setCurrentQuizNumber(currentQuizNumber + 1);
  }
  function checkAnswer(): boolean {
    const isCorrect =
      Number(quizData[currentQuizNumber - 1].answer.number) ===
      answerList[currentQuizNumber - 1];
    console.log("정답 : ", quizData[currentQuizNumber - 1].answer.number);
    console.log("anawerList : ", answerList);
    console.log("내가 고른 거 : ", answerList[currentQuizNumber - 1]);
    if (isCorrect) {
      setIsCorrect(true);
    } else {
      setIsUnCorrect(true);
    }
    setCorrectList((prev) => {
      let newArr = prev.slice();
      newArr[currentQuizNumber - 1] = isCorrect;
      return newArr;
    });
    return isCorrect;
  }
  return (
    <>
      <button onClick={() => console.log(answerList)}>asd</button>
      <div className={cx("quiz-container", "not-solve")}>
        <Timer currentQuizNumber={currentQuizNumber} />
        {isCorrect && (
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

        {isUnCorrect && (
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
        <div className={cx("quiz-content")}>
          <h4 className={cx("question")}>
            <div>{currentQuizNumber}. </div>
            {/* currentQuizNumber가 0번(커버)부터 시작이라서 -1을 해줌 */}
            <div>{quizData[currentQuizNumber - 1].question.questionText}</div>
          </h4>
          <ul className={cx("case-container")}>
            {Object.entries<string>(
              quizData[currentQuizNumber - 1].question.choices
            ).map(([key, value]: [string, string]) => (
              <li
                onClick={() => selectAnswer(Number(key))}
                key={key}
                className={cx("case")}
              >
                <div
                  className={cx("case__number", {
                    answer: answerList[currentQuizNumber - 1] === Number(key),
                  })}
                >
                  ({key})
                </div>
                <div className={cx("case__item")}>{value}</div>
              </li>
            ))}
          </ul>
          <div className={cx("btn-container")}>
            <button onClick={checkAnswer} className={cx("next-btn")}>
              <div>다음</div>
              <div className={cx("next-icon")}>
                <img src="/next-icon.svg" alt="" />
              </div>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
