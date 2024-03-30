import { Dispatch, SetStateAction, useRef } from "react";
import styled from "./Summary.module.css";
import classNames from "classnames/bind";
import { text } from "stream/consumers";
import { privateFetch } from "@/utils/http-commons";
export default function Summary({
  setUserSummary,
  solveQuiz,
  isSolved,
}: {
  setUserSummary: Dispatch<SetStateAction<string>>;
  solveQuiz: Function;
  isSolved: boolean;
}) {
  const cx = classNames.bind(styled);
  const textareaEl = useRef<HTMLTextAreaElement>(null);

  // METHOD
  function userSummaryHandler() {
    setUserSummary(textareaEl.current?.value || "");
  }
  return (
    <>
      <div className={cx("container")}>
        <div className={cx("summary-container")}>
          {!isSolved ? (
            <div onClick={() => solveQuiz()} className={cx("next-btn")}>
              제출
            </div>
          ) : null}
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
                <>
                  {
                    <>
                      "현직 교사들이 사교육 업체에게 금품을 받고 모의고사 문제를
                      넘긴 '사교육 카르텔' 의혹이 감사원 감사에서 사실로
                      드러났다.
                      <br />
                      <br />
                      수능 출제 또는 EBS 수능 연계교재 집필에 참여한 다수 교사가
                      사교육 업체와 문항을 거래한 것도 이번 감사에서 드러났다.
                      <br />
                      <br />
                      감사원은 "교원과 사교육 업체 간 문항 거래는 수능 경향에
                      맞춘 양질의 문항을 공급받으려는 사교육 업체와 금전적
                      이익을 원하는 일부 교원 간에 금품 제공을 매개로 뿌리 깊게
                      자리 잡고 있음을 확인했다"고 밝혔다."
                    </>
                  }
                </>
              ) : (
                <img src="/lock-icon.svg" alt="" />
              )}
            </div>
          </div>
          <div className={cx("user-summary", ["box-shodow-custom"])}>
            <h3 className={cx("title")}>
              <div>
                <img src="/dog-summary.svg" alt="myDog" />
              </div>
              <div>나의 요약</div>
            </h3>
            <div className={cx("content")}>
              <textarea
                onChange={userSummaryHandler}
                ref={textareaEl}
                maxLength={200}
                className={cx("textarea")}
                cols={30}
                rows={10}
              ></textarea>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
