import { Dispatch, SetStateAction } from "react";
import styled from "./Quiz.module.css";
import classNames from "classnames/bind";
interface Props {
  setCurrentQuiz: Dispatch<SetStateAction<number>>;
  quizData: any[];
  currentQuizNumber: number;
}
export default function Quiz({
  setCurrentQuiz,
  quizData,
  currentQuizNumber,
}: Props) {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={cx("quiz-container")}>
        <div className={cx("quiz-content")}>
          <h4 className={cx("question")}>
            <div>{currentQuizNumber}. </div>
            <div>{quizData[currentQuizNumber].question.questionText}</div>
          </h4>
          <ul className={cx("case-container")}>
            {Object.entries<string>(
              quizData[currentQuizNumber].question.choices
            ).map(([key, value]: [string, string]) => (
              <li key={key} className={cx("case")}>
                <div className={cx("case__number")}>({key})</div>
                <div className={cx("case__item")}>{value}</div>
              </li>
            ))}
          </ul>
          <div className={cx("btn-container")}>
            <button className={cx("next-btn")}>
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
