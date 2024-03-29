import NewsContent from "./NewsContent";
import Cover from "./Cover";
import Quiz from "./Quiz";
import Summary from "./Summary";
import { Dispatch, SetStateAction, useState } from "react";
import Highlighter from "@/assets/highlighter-icon.svg";
export default function NotSolved({
  data,
  currentQuizNumber,
  setCurrentQuizNumber,
  keywords,
  quizData,
  cx, // newsDetailContainer.module.css
}: {
  data: any;
  currentQuizNumber: number;
  keywords: string[];
  setCurrentQuizNumber: Dispatch<SetStateAction<number>>;
  cx: Function;
  quizData: any[];
}) {
  // 0 : 형광펜 , 1 : 돋보기, 2 : 지우개
  const [cursor, setCursor] = useState<number>(0);
  // METHOD
  function changeCursor(cursorToChange: number) {
    setCursor(cursorToChange);
  }
  return (
    <div className={cx("container")}>
      <div className={cx("detail-container", ["box-shodow-custom"])}>
        <div
          className={cx(
            "pentool",
            {
              highlight: cursor === 0,
              finder: cursor === 1,
              eraser: cursor === 2,
            },
            ["box-shodow-custom"]
          )}
        >
          <div
            onClick={() => changeCursor(0)}
            className={cx("pentool__item", {
              active: cursor === 0,
              highlight: cursor === 0,
              finder: cursor === 1,
              eraser: cursor === 2,
            })}
          >
            {/* <Highlighter /> */}
            <img src="/highlighter-icon.svg" alt="" />
          </div>
          <div
            onClick={() => changeCursor(1)}
            className={cx("pentool__item", {
              active: cursor === 1,
              highlight: cursor === 0,
              finder: cursor === 1,
              eraser: cursor === 2,
            })}
          >
            <img src="/search-icon.svg" alt="" />
          </div>
          <div
            onClick={() => changeCursor(2)}
            className={cx("pentool__item", {
              active: cursor === 2,
              highlight: cursor === 0,
              finder: cursor === 1,
              eraser: cursor === 2,
            })}
          >
            <img src="/eraser-icon.svg" alt="" />
          </div>
        </div>
        <NewsContent cursor={cursor} keywords={keywords} data={data} />
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
