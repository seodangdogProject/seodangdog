"use client";
import style from "./NavBar.module.css";
export default function NavBar() {
  return (
    <>
      <nav className={style.nav}>
        <h3 className={style.logo}>서당독</h3>
        <ul className={style.item_container}>
          <li className={style.item}>메인페이지</li>
          <li>대시보드</li>
          <li>단어장</li>
          <li>게임</li>
        </ul>
      </nav>
    </>
  );
}
