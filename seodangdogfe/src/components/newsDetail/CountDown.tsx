import classNames from "classnames/bind";
import styled from "./CountDown.module.css";
export default function CountDown() {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={cx("countdown")}>
        <div className={cx("number")}>
          <h2>3</h2>
        </div>

        <div className={cx("number")}>
          <h2>2</h2>
        </div>

        <div className={cx("number")}>
          <h2>1</h2>
        </div>
      </div>
    </>
  );
}
