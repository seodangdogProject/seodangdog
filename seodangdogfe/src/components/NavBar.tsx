"use client";

// next 모듈
import Image from "next/image";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";

// 외부 모듈
import classNames from "classnames/bind";

// 내부 모듈
import style from "./NavBar.module.css";

// SVG 파일
import MainIcon from "../assets/main-icon.svg";
import DashboardIcon from "../assets/dashboard-icon.svg";
import DictIcon from "../assets/dict-icon.svg";
import GameIcon from "../assets/game-icon.svg";
import LogoutIcon from "../assets/logout-icon.svg";
import HottopicIcon from "../assets/hottopic-icon.svg";
import { useEffect, useState } from "react";
export default function NavBar() {
  const cx = classNames.bind(style);
  const [active, setActive] = useState<string>("");
  const [isLogout, setIsLogout] = useState<boolean>(false);
  const pathname: string = usePathname();
  const router = useRouter();
  useEffect(() => {
    setActive(pathname.split("/")[1]);
  }, [pathname]);

  useEffect(() => {
    if (isLogout) {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      router.replace("/");
    }
  }, [isLogout]);

  // method
  function handleLogout() {
    setIsLogout(true);
  }

  return (
    <>
      <nav className={style.nav}>
        <Link href="/main">
          <h3 className={style.logo}>서당독</h3>
        </Link>
        <ul className={style.item_container}>
          <Link href="/main">
            <li
              className={cx("item", {
                active: active === "main",
              })}
            >
              <div>
                <MainIcon />
              </div>
              <div>홈</div>
            </li>
          </Link>
          <Link href="/hottopic">
            <li
              className={cx("item", {
                active: active === "hottopic",
                hottopic: active === "hottopic",
              })}
            >
              <div className={cx("hottopic")}>
                <HottopicIcon />
              </div>
              <div>핫토픽</div>
            </li>
          </Link>
          
          <Link href="/word_list">
            <li
              className={cx("item", {
                active: active === "word_list",
              })}
            >
              <div>
                <DictIcon />
              </div>
              <div>단어장</div>
            </li>
          </Link>
          <Link href="/word_game">
            <li
              className={cx("item", {
                active: active === "word_game",
              })}
            >
              <div>
                <GameIcon />
              </div>
              <div>게임</div>
            </li>
          </Link>
          <Link href="/dashboard">
            <li
              className={cx("item", {
                active: active === "dashboard",
              })}
            >
              <div>
                <DashboardIcon />
              </div>
              <div>내 정보</div>
            </li>
          </Link>
        </ul>
        
        <div className={cx("logout")}>
          <div onClick={handleLogout} className={cx("item", "logout_item")}>
            <div>
              <LogoutIcon />
            </div>
            <div>로그아웃</div>
          </div>
        </div>
      </nav>
    </>
  );
}
