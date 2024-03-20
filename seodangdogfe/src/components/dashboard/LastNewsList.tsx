import styled from "./LastNewsList.module.css";
import classNames from "classnames/bind";
function CardNews() {
  return (
    <div className={styled.cardNews}>
      <img
        src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
        alt=""
      />
      <div className={styled.desc}>
        <div className={styled.title}>
          기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
        </div>
        <div className={styled.meta}>조회수 1만회 • 3 시간 전</div>
        <div className={styled.company}>
          <img
            src="https://mimgnews.pstatic.net/image/upload/office_logo/018/2018/08/08/logo_018_57_20180808174308.png"
            alt=""
          />
        </div>
        <div className={styled.summary}>
          기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의 경험을
          선보인다. △전기차 충전 △공간 및 신기술 △지속가능성 등을...
        </div>
        <ul className={styled.hashtag}>
          <li className={styled.item}># 은행</li>
          <li className={styled.item}># 자동차</li>
          <li className={styled.item}># 기아</li>
        </ul>
      </div>
    </div>
  );
}
export default function LastNewsList() {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={styled.container}>
        <div className={cx("box,", ["box-shodow-custom"])}>
          <div className={styled.head}>
            <h3>최근 본 기사</h3>
            <img src="/prev-arrow-icon.svg" alt="" />
          </div>
          <div className={styled.content}>
            {/* <ul className={styled.cardContainer}>
              <li>
                <CardNews />
              </li>
              <li>
                <CardNews />
              </li>
            </ul> */}
            <CardNews />
            <CardNews />
            <CardNews />
          </div>
        </div>
      </div>
    </>
  );
}
