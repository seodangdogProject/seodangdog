import { Dispatch, SetStateAction } from "react";
import NewsContent from "./solved/NewsContent";
import Quiz from "./solved/Quiz";
import Summary from "./Summary";

export default function Solved({
  data,
  newsSeq,
  keywords,
  quizData,
  answerList,
  cx,
  currentQuizNumber,
  setCurrentQuizNumber,
}: {
  data: any;
  newsSeq: number;
  keywords: any[];
  quizData: any[];
  answerList: number[];
  currentQuizNumber: number;
  cx: Function;
  setCurrentQuizNumber: Dispatch<SetStateAction<number>>;
}) {
  return (
    <>
      <div className={cx("container")}>
        <div
          tabIndex={-1}
          className={cx("detail-container", ["box-shodow-custom"])}
        >
          <NewsContent data={data} keywords={keywords} />
          <div className={cx("paging")}>
            <ul>
              {[1, 2, 3, 4].map((paging) => (
                <li
                  key={paging}
                  onClick={() => setCurrentQuizNumber(paging)}
                  className={cx({
                    "paging-active": currentQuizNumber === paging,
                  })}
                ></li>
              ))}
            </ul>
          </div>
          {currentQuizNumber < 4 ? (
            <Quiz
              currentQuizNumber={currentQuizNumber}
              answerList={answerList}
              quizData={quizData}
            />
          ) : (
            <Summary
              isSolved={true}
              setUserSummary={data.userSummary.userSummaryContent} // 어차피 실행 안될거라서 막 넣어줌
              newsSummary={data.newsSummary[0]}
              userSummary={data.userSummary.userSummaryContent}
            />
          )}
        </div>
      </div>
    </>
  );
}
