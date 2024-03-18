import { Dispatch, SetStateAction } from "react";
import styled from "./Cover.module.css";
import classNames from "classnames/bind";
interface Props {
  goNext: Dispatch<SetStateAction<number>>;
}
export default function Cover({ goNext }: Props) {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={styled["quiz-container"]}>
        <img
          src="/cover-icon.svg"
          alt="coverPage"
          onClick={() => goNext((prev) => prev + 1)}
        />
      </div>
    </>
  );
}
