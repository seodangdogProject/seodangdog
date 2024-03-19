import styled from "./LastNewsList.module.css";
import classNames from "classnames/bind";
function CardNews() {
  return (
    <div className={styled.cardNews}>
      <img
        src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
        alt=""
      />
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
