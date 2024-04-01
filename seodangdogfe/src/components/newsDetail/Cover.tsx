import { Dispatch, SetStateAction, useState } from "react";
import styled from "./Cover.module.css";
import classNames from "classnames/bind";
import CountDown from "./CountDown";
interface Props {
  setCurrentQuiz: Dispatch<SetStateAction<number>>;
}
export default function Cover({ setCurrentQuiz }: Props) {
  const cx = classNames.bind(styled);
  const [gameStart, setGameStart] = useState(false);

  function gameStartHandler() {
    setGameStart(true);
    setTimeout(() => {
      setCurrentQuiz(1);
    }, 3000);
  }
  return (
    <>
      <div className={styled["cover-container"]}>
        {gameStart ? (
          <CountDown />
        ) : (
          <div className={cx("cover-content")}>
            <img
              src="/cover-icon.svg"
              alt="coverPage"
              onClick={gameStartHandler}
            />
          </div>
        )}
      </div>
    </>
  );
}
