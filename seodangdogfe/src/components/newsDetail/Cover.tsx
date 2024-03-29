import { Dispatch, SetStateAction } from "react";
import styled from "./Cover.module.css";
import classNames from "classnames/bind";
interface Props {
  setCurrentQuiz: Dispatch<SetStateAction<number>>;
}
export default function Cover({ setCurrentQuiz }: Props) {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={styled["cover-container"]}>
        <div className={cx("cover-content")}>
          <img
            src="/cover-icon.svg"
            alt="coverPage"
            onClick={() => setCurrentQuiz(1)}
          />
        </div>
      </div>
    </>
  );
}
