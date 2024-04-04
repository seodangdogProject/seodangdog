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

  function checkAnswer(): boolean {
    console.log("checkAnswer");
    const isCorrect =
      Number(quizData[currentQuizNumber - 1].answer.number) ===
      answerList[currentQuizNumber - 1];

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

  function moveToNextQuiz() {
    const answerIsCorrect = checkAnswer();

    setTimeout(() => {
      setCurrentQuizNumber(currentQuizNumber + 1);
    }, 1000);
  }
  return (
    <>
      <div className={cx("quiz-container", "not-solve")}>
        {isCorrect && (
          <CorrectIcon
            style={{
              position: "absolute",
              top: "50%",
              left: "77%",
              marginLeft: "5%",
              transform: "translate(-50%, -50%)",
              zindex: 1000,
            }}
          ></CorrectIcon>
        )}

        {isUnCorrect && (
          <UncorrectIcon
            style={{
              position: "absolute",
              top: "50%",
              left: "77%",
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
                    answer:
                      answerList &&
                      answerList[currentQuizNumber - 1] === Number(key),
                  })}
                >
                  ({key})
                </div>
                <div className={cx("case__item")}>{value}</div>
              </li>
            ))}
          </ul>
          <div className={cx("btn-container")}>
            <button onClick={moveToNextQuiz} className={cx("next-btn")}>
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
