import styled from "./Quiz.module.css";
import classNames from "classnames/bind";
export default function Quiz() {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={cx("container")}>
        <h4 className={cx("question")}>
          <div>1.</div>
          <div>위 기사에서 언급된 “수급상황실”은 무엇을 의미합니까?</div>
        </h4>
        <div className={cx("case-container")}>
          <div className={cx("case")}>(A) 공급량 조절을 위한 부서</div>
          <div className={cx("case")}>(B) 긴급한 재고 확인 장소</div>
          <div className={cx("case")}>
            (C) 물품 수요 및 공급 상활을 모니터링하는 시설
          </div>
          <div className={cx("case")}>(D) 가격 변동에 따른 수요 조절기구</div>
        </div>
        <div className={cx("btn-container")}>
          <button className={cx("next-btn")}>
            <div>다음</div>
            <div className={cx("next-icon")}>
              <img src="/next-icon.svg" alt="" />
            </div>
          </button>
        </div>
      </div>
    </>
  );
}
