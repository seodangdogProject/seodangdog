import NewsContent from "./NewsContent";
import Cover from "./Cover";
import Quiz from "./Quiz";
import Summary from "./Summary";
import { Dispatch, SetStateAction } from "react";
export default function NotSolved({
  data,
  currentQuizNumber,
  setCurrentQuizNumber,
  keywords,
  quizData,
  cx,
}: {
  data: any;
  currentQuizNumber: number;
  keywords: string[];
  setCurrentQuizNumber: Dispatch<SetStateAction<number>>;
  cx: Function;
  quizData: any[];
}) {
  return (
    <div className={cx("container")}>
      <div className={cx("detail-container", ["box-shodow-custom"])}>
        <NewsContent keywords={keywords} data={data} />
        {currentQuizNumber === 0 ? (
          <Cover setCurrentQuiz={setCurrentQuizNumber} />
        ) : currentQuizNumber < 4 ? (
          <Quiz
            currentQuizNumber={currentQuizNumber}
            quizData={quizData}
            setCurrentQuizNumber={setCurrentQuizNumber}
          />
        ) : (
          <Summary isSolved={false} />
        )}
      </div>
    </div>
  );
}
