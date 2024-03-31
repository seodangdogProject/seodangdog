import styled from "../Quiz.module.css";
import classNames from "classnames/bind";
export default function Quiz({
  currentQuizNumber,
  quizData,
  answerList,
}: {
  currentQuizNumber: number;
  quizData: any[];
  answerList: number[];
}) {
  const cx = classNames.bind(styled);
  return (
    <>
      {currentQuizNumber !== 0 && (
        <div className={cx("quiz-container")}>
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
                <li key={key} className={cx("case")}>
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
              {/* <button onClick={moveToNextQuiz} className={cx("next-btn")}>
              <div>다음</div>
              <div className={cx("next-icon")}>
                <img src="/next-icon.svg" alt="" />
              </div>
            </button> */}
            </div>
          </div>
        </div>
      )}
    </>
  );
}
