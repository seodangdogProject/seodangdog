import NewsContent from "./NewsContent";
import Cover from "./Cover";
import Quiz from "./Quiz";
import Summary from "./Summary";
import { Dispatch, SetStateAction, useEffect, useRef, useState } from "react";
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
  const detailContainerEl = useRef<HTMLDivElement>(null);
  // 0 : 형광펜 , 2 : 지우개, 2 : 돋보기
  const [cursor, setCursor] = useState<number>(0);
  // METHOD
  function changeCursor(cursorToChange: number) {
    setCursor(cursorToChange);
  }
  function nextCursor(e: any) {
    console.log(e);
    if (e.keyCode === 32 || 17) setCursor((prev) => (prev + 1) % 3);
  }
  useEffect(() => {
    detailContainerEl.current?.focus();
  }, []);
  return (
    <div className={cx("container")}>
      <div
        tabIndex={-1}
        id="newsDetailFocus"
        onKeyDown={(e) => nextCursor(e)}
        ref={detailContainerEl}
        className={cx("detail-container", ["box-shodow-custom"])}
      >
        {/* 커서 변환 모달 START*/}
        <div
          className={cx(
            "pentool",
            {
              highlight: cursor === 0,
              finder: cursor === 2,
              eraser: cursor === 1,
            },
            ["box-shodow-custom"]
          )}
        >
          <div
            onClick={() => changeCursor(0)}
            className={cx("pentool__item", {
              active: cursor === 0,
              highlight: cursor === 0,
              finder: cursor === 2,
              eraser: cursor === 1,
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
              finder: cursor === 2,
              eraser: cursor === 1,
            })}
          >
            <img src="/eraser-icon.svg" alt="" />
          </div>
          <div
            onClick={() => changeCursor(2)}
            className={cx("pentool__item", {
              active: cursor === 2,
              highlight: cursor === 0,
              finder: cursor === 2,
              eraser: cursor === 1,
            })}
          >
            <img src="/search-icon.svg" alt="" />
          </div>
        </div>
        {/* 커서 변환 모달 END*/}
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
