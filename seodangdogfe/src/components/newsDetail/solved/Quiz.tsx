import { Dispatch, SetStateAction } from "react";
import styled from "../Quiz.module.css";
import classNames from "classnames/bind";
export default function Quiz({
  currentQuizNumber,
  quizData,
  answerList,
  setCurrentQuizNumber,
}: {
  currentQuizNumber: number;
  quizData: any[];
  answerList: number[];
  setCurrentQuizNumber: Dispatch<SetStateAction<number>>;
}) {
  const cx = classNames.bind(styled);
  return (
    <>
      {currentQuizNumber !== 0 && (
        <div className={cx("quiz-container")}>
          <div className={cx("quiz-content")}>
            <h4 className={cx("question")}>
              {/* 정답이 맞았으면 맞은 표시를 틀렸으면 틀린 이지미를 랜더링 해주는 부분 */}
              {Number(quizData[currentQuizNumber - 1].answer.number) ===
              answerList[currentQuizNumber - 1] ? (
                <img
                  className={cx("right-circle")}
                  src="/answer-right-circle.svg"
                  alt=""
                />
              ) : (
                <img
                  className={cx("wrong-line")}
                  src="/answer-wrong-line.svg"
                  alt=""
                />
              )}
              <div>{currentQuizNumber}. </div>
              {/* currentQuizNumber가 0번(커버)부터 시작이라서 -1을 해줌 */}
              <div>{quizData[currentQuizNumber - 1].question.questionText}</div>
            </h4>
            <ul className={cx("case-container")}>
              {Object.entries<string>(
                quizData[currentQuizNumber - 1].question.choices
              ).map(([key, value]: [string, string]) => (
                <li key={key} className={cx("case", "solved")}>
                  <div
                    className={cx("case__number", {
                      answer: answerList[currentQuizNumber - 1] === Number(key),
                      isAnswer:
                        !(answerList[currentQuizNumber - 1] === Number(key)) &&
                        quizData[currentQuizNumber - 1].answer.number === key,
                    })}
                  >
                    ({key})
                  </div>
                  <div className={cx("case__item")}>{value}</div>
                </li>
              ))}
            </ul>
            <div className={cx("btn-container")}>
              {currentQuizNumber !== 1 && (
                <button
                  onClick={() => setCurrentQuizNumber((prev) => prev - 1)}
                  className={cx("next-btn")}
                >
                  <div className={cx("prev-icon")}>
                    <img src="/prev-quiz-icon.svg" alt="" />
                  </div>
                  <div>이전</div>
                </button>
              )}
              <button
                onClick={() => setCurrentQuizNumber((prev) => prev + 1)}
                className={cx("next-btn")}
              >
                <div>다음</div>
                <div className={cx("next-icon")}>
                  <img src="/next-icon.svg" alt="" />
                </div>
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
