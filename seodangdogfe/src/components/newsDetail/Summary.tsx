import { Dispatch, SetStateAction, useRef } from "react";
import styled from "./Summary.module.css";
import classNames from "classnames/bind";
import { text } from "stream/consumers";
import { privateFetch } from "@/utils/http-commons";
export default function Summary({
  setUserSummary,
  solveQuiz,
  isSolved,
  newsSummary,
  userSummary,
  setCurrentQuizNumber,
}: {
  setUserSummary: Dispatch<SetStateAction<string>>;
  solveQuiz?: Function;
  isSolved: boolean;
  newsSummary: string[];
  userSummary?: string;
  setCurrentQuizNumber: Dispatch<SetStateAction<number>>;
}) {
  const cx = classNames.bind(styled);
  const textareaEl = useRef<HTMLTextAreaElement>(null);
  const texts = ["기사를 읽고 요약문을 작성해보세요."];

  // METHOD
  function userSummaryHandler() {
    setUserSummary(textareaEl.current?.value || "");
  }
  return (
    <>
      <div className={cx("container")}>
        <div className={cx("summary-container")}>
          {/* {!isSolved && solveQuiz !== undefined ? (
            <div onClick={() => solveQuiz()} className={cx("next-btn")}>
              제출
            </div>
          ) : null} */}
          {!isSolved && (
            <div className={cx("desc-text")}>
              기사를 읽고 요약문을 작성해보세요.
            </div>
          )}

          <div className={cx("user-summary", ["box-shodow-custom"])}>
            {!isSolved && solveQuiz !== undefined ? (
              <div onClick={() => solveQuiz()} className={cx("next-btn")}>
                제출
              </div>
            ) : null}
            <h3 className={cx("title")}>
              <div>
                <img src="/dog-summary.svg" alt="myDog" />
              </div>
              <div>나의 요약</div>
            </h3>
            <div className={cx("content")}>
              {!isSolved ? (
                <textarea
                  onChange={userSummaryHandler}
                  ref={textareaEl}
                  maxLength={200}
                  className={cx("textarea")}
                  cols={30}
                  rows={10}
                ></textarea>
              ) : (
                <>{userSummary}</>
              )}
            </div>
          </div>
          <div className={cx("naver-summary")}>
            <h3 className={cx("title")}>
              <div>
                <img src="/naver-summary-bot.svg" alt="naverBot" />
              </div>
              <div>
                <span className={cx("naver-color")}>네이버 </span>요약봇
              </div>
            </h3>
            <div className={cx("content", { lock: !isSolved })}>
              {isSolved ? (
                newsSummary.map((item) => (
                  <>
                    {item}
                    <br></br>
                  </>
                ))
              ) : (
                // <>{newsSummary}</>
                <img src="/lock-icon.svg" alt="" />
              )}
            </div>
          </div>

          {isSolved && (
            <div className={cx("btn-container")}>
              <button
                onClick={() => setCurrentQuizNumber((prev) => prev - 1)}
                className={cx("btn")}
              >
                <div className={cx("prev-icon")}>
                  <img src="/prev-quiz-icon.svg" alt="" />
                </div>
                <div>이전</div>
              </button>
            </div>
          )}
        </div>
      </div>
    </>
  );
}
