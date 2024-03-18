import styled from "./Cover.module.css";
import classNames from "classnames/bind";
export default function Cover() {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={styled["quiz-container"]}>
        <img src="/cover-icon.svg" alt="coverPage" />
      </div>
    </>
  );
}
