import classNames from "classnames/bind";
import styled from "./loading.module.css";
export default function Loading() {
  const cx = classNames.bind(styled);
  return (
    <div className={cx("body")}>
      <div className={cx("pl")}>
        <div className={cx("pl__bars")}>
          <div className={cx("pl__bar")}>
            <div className={cx("pl__bar-s")}></div>
            <div className={cx("pl__bar-t")}></div>
            <div className={cx("pl__bar-l")}></div>
            <div className={cx("pl__bar-r")}></div>
          </div>
          <div className={cx("pl__bar")}>
            <div className={cx("pl__bar-s")}></div>
            <div className={cx("pl__bar-t")}></div>
            <div className={cx("pl__bar-l")}></div>
            <div className={cx("pl__bar-r")}></div>
          </div>
          <div className={cx("pl__bar")}>
            <div className={cx("pl__bar-s")}></div>
            <div className={cx("pl__bar-t")}></div>
            <div className={cx("pl__bar-l")}></div>
            <div className={cx("pl__bar-r")}></div>
          </div>
          <div className={cx("pl__bar")}>
            <div className={cx("pl__bar-s")}></div>
            <div className={cx("pl__bar-t")}></div>
            <div className={cx("pl__bar-l")}></div>
            <div className={cx("pl__bar-r")}></div>
          </div>
        </div>
      </div>
    </div>
  );
}
