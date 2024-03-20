import styled from "./UserCard.module.css";
import classNames from "classnames/bind";
export default function UserCard() {
  const cx = classNames.bind(styled);
  return (
    <div className={cx("container", ["box-shodow-cutom"])}>
      <div className={styled.king}></div>
      <div className={styled.name}>아기아림</div>
      <div className={styled.email}>babyarim@naver.com</div>
      <ul className={styled.hashtag}>
        <li className={styled.item}># 어휘왕</li>
        <li className={styled.item}># 추론왕</li>
        <li className={styled.item}># 단어왕</li>
      </ul>
    </div>
  );
}
