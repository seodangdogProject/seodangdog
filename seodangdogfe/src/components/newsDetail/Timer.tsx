import classNames from "classnames/bind";
import styled from "./Timer.module.css";
import { useEffect, useRef, useState } from "react";
export default function Timer({
  currentQuizNumber,
}: {
  currentQuizNumber: number;
}) {
  const cx = classNames.bind(styled);
  const [width, setWidth] = useState("0%");
  let width1 = "0%";
  const [color, setColor] = useState("#4caf50");
  const timerBarEl = useRef<HTMLDivElement>(null);
  useEffect(() => {
    width1 = "100%";
    const timer = setTimeout(() => {
      setColor(
        "linear-gradient( to right, #4caf50 0%, #4caf50 85%, #4caf50 85%, #ff5733 100%"
      );
    }, 12000);
    // const timer3 = setTimeout(() => {
    //   setWidth("0%");
    // }, 15000);
    return () => clearTimeout(timer);
  }, [currentQuizNumber]);
  // useEffect(() => {
  //   setWidth("0%");
  // }, [currentQuizNumber]);
  return (
    <>
      <div className={cx("container")}>
        <div className={cx("timer-img")}>
          <img src="/timer-icon.svg" alt="" />
        </div>
        <div className={cx("timer-container")}>
          <div
            className={cx("timer-bar")}
            style={{ width, background: color }}
          ></div>
        </div>
      </div>
    </>
  );
}
