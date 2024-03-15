"use client";
import React, { useState, useEffect, useCallback } from "react";
import { useRouter } from "next/navigation";
import { useRecoilState, RecoilRoot } from "recoil";

import { userKeywords } from "../../../atoms/joinRecoil";
import Link from "next/link";
import styles from "./game_layout.module.css";
import JoinModal from "../../../components/joinComponent/joinModal";

interface FallingLetter {
  id: number;
  keyword: string;
  x: number;
  y: number;
  isCaught: boolean;
  isShown: boolean;
  speed: number;
}

export default function Join() {
  const router = useRouter();
  const [isOpenModal, setOpenModal] = useState<boolean>(false);
  const [fallingLetters, setFallingLetters] = useState<FallingLetter[]>([]);
  const [userKeywords, setUserKeywords] = useState<
    { id: number; keyword: string }[]
  >([]);
  const [keywordListIndex, setKeywordListIndex] = useState(0);
  const [startIndex, setStartIndex] = useState(0);
  const [unLock, setUnLock] = useState(true);
  const [userKeywordsSize, setUserKeywordsSize] = useState(0);

  function lockToggle() {
    setUnLock(false);
  }

  const onClickToggleModal = useCallback(() => {
    setOpenModal(!isOpenModal);
  }, [isOpenModal]);

  const [xy, setXY] = useState({ x: 0, y: 0 });

  const xyHandler: React.MouseEventHandler<HTMLDivElement> = (e) => {
    const mouseX = e.clientX;
    const mouseY = e.clientY;

    setXY({ x: mouseX, y: mouseY });
  };

  useEffect(() => {
    const keywordList = [
      { id: 1, keyword: "사과" },
      { id: 2, keyword: "바나나" },
      { id: 3, keyword: "딸기" },
      { id: 4, keyword: "포도" },
      { id: 5, keyword: "수박" },
      { id: 6, keyword: "오렌지" },
      { id: 7, keyword: "파인애플" },
      { id: 8, keyword: "체리" },
      { id: 9, keyword: "멜론" },
      { id: 10, keyword: "레몬" },
      { id: 11, keyword: "라임" },
      { id: 12, keyword: "복숭아" },
      { id: 13, keyword: "배" },
      { id: 14, keyword: "키위" },
      { id: 15, keyword: "밤" },
      { id: 16, keyword: "자두" },
      { id: 17, keyword: "체리" },
      { id: 18, keyword: "오렌지" },
      { id: 19, keyword: "수박" },
      { id: 20, keyword: "딸기" },
      { id: 21, keyword: "사과" },
      { id: 22, keyword: "바나나" },
      { id: 23, keyword: "파인애플" },
      { id: 24, keyword: "포도" },
      { id: 25, keyword: "배" },
      { id: 26, keyword: "체리" },
      { id: 27, keyword: "키위" },
      { id: 28, keyword: "멜론" },
      { id: 29, keyword: "오렌지" },
      { id: 30, keyword: "밤" },
      { id: 31, keyword: "자두" },
      { id: 32, keyword: "복숭아" },
      { id: 33, keyword: "딸기" },
      { id: 34, keyword: "파인애플" },
      { id: 35, keyword: "수박" },
      { id: 36, keyword: "라임" },
      { id: 37, keyword: "사과" },
      { id: 38, keyword: "바나나" },
      { id: 39, keyword: "딸기" },
      { id: 40, keyword: "포도" },
      { id: 41, keyword: "체리" },
      { id: 42, keyword: "오렌지" },
      { id: 43, keyword: "파인애플" },
      { id: 44, keyword: "멜론" },
      { id: 45, keyword: "레몬" },
      { id: 46, keyword: "복숭아" },
      { id: 47, keyword: "배" },
      { id: 48, keyword: "키위" },
      { id: 49, keyword: "밤" },
      { id: 50, keyword: "자두" },
    ];

    const createFallingLetter = (keywordItem: {
      id: number;
      keyword: string;
    }) => {
      const randomSpeed = Math.random() * 2;
      return {
        id: keywordItem.id,
        keyword: keywordItem.keyword,
        x: 10 + Math.random() * (window.innerWidth - 70),
        y: 0,
        isCaught: false,
        isShown: true,
        speed: randomSpeed,
      };
    };

    const initialFallingLetters: FallingLetter[] = keywordList
      .slice(startIndex, startIndex + 10)
      .map((keywordItem) => createFallingLetter(keywordItem));

    setFallingLetters(initialFallingLetters);

    const fallingInterval = setInterval(() => {
      let newStartIndex = startIndex + 10;
      console.log(newStartIndex);
      if (newStartIndex >= keywordList.length) {
        console.log("리스트 끝남");
        clearInterval(fallingInterval);
        return; // Interval 종료
      }

      let newFallingLetters: FallingLetter[];
      if (newStartIndex + 10 >= keywordList.length) {
        newFallingLetters = keywordList
          .slice(newStartIndex, keywordList.length) // startIndex 대신 newStartIndex 사용
          .map((keywordItem) => createFallingLetter(keywordItem));
      } else {
        newFallingLetters = keywordList
          .slice(newStartIndex, newStartIndex + 10) // startIndex 대신 newStartIndex 사용
          .map((keywordItem) => createFallingLetter(keywordItem));
      }

      setFallingLetters((prevLetters) => [
        ...prevLetters,
        ...newFallingLetters,
      ]);

      setStartIndex(newStartIndex);
    }, 6000);

    const update = () => {
      setFallingLetters((prevLetters) =>
        prevLetters
          .map((letter) => ({
            ...letter,
            y: letter.y + letter.speed,
            isShown: letter.y < innerHeight - 100,
          }))
          .filter((letter) => letter.isShown && !letter.isCaught)
      );
      requestAnimationFrame(update);
    };

    setFallingLetters(initialFallingLetters);

    const animationId = requestAnimationFrame(update);

    return () => {
      clearInterval(fallingInterval);
      cancelAnimationFrame(animationId);
    };
  }, [keywordListIndex, startIndex]);

  const handleLetterClick = async (id: number, keyword: string) => {
    console.log(keyword, " 잡음");
    setUserKeywords((prevKeywords) => [...prevKeywords, { id, keyword }]);
    setUserKeywordsSize((count) => count + 1);
    setFallingLetters((prevLetters) =>
      prevLetters.map((letter) =>
        letter.id === id ? { ...letter, isCaught: true, isShown: true } : letter
      )
    );
    console.log(userKeywordsSize);

    if (userKeywordsSize >= 9) {
      lockToggle();
    }
  };

  return (
    <>
      <div
        className={`${styles.stage_bg} ${styles.mouse}`}
        onMouseMove={xyHandler}
        style={{
          width: "100%",
          height: "100vh",
          backgroundSize: "cover", // 이미지가 요소에 맞게 자동으로 조절되도록 cover 값을 설정합니다.
          backgroundPosition: "center", // 이미지를 가운데 정렬합니다.
          backgroundImage:
            "url(https://images.unsplash.com/photo-1628006203055-b4aa5f6300f3?q=60&w=2000",
        }}
      >
        {isOpenModal && (
          <JoinModal
            data={userKeywords}
            onClickToggleModal={onClickToggleModal}
          ></JoinModal>
        )}
        <div
          className={styles.pointer}
          style={{
            transform: `translate(${xy.x}px, ${xy.y}px)`,
          }}
        />
        {fallingLetters.map(
          (letter) =>
            letter.isShown && (
              <div
                key={letter.id} // 요소의 id를 고유한 키로 사용
                style={{
                  color: "black",
                  position: "absolute",
                  top: letter.y,
                  left: letter.x,
                  fontSize: "60px",
                  zIndex: 1, // 클릭 가능하도록 다른 요소보다 위에 표시
                }}
                onClick={() => handleLetterClick(letter.id, letter.keyword)}
              >
                {letter.keyword}
              </div>
            )
        )}
        <div
          style={{
            fontSize: "50px",
            position: "absolute",
            top: 40,
            left: 40,
            color: "white",
            zIndex: "",
          }}
        >
          <p>
            담은 개수 : {userKeywords.length}
            {/* 잡은 키워드 :  */}
            {/* {userKeywords
                        .map((keyword) => `${keyword.keyword}(${keyword.id})`)
                        .join(', ')} */}
          </p>
        </div>
        <Link href="/join_game">
          <div
            onClick={onClickToggleModal}
            style={{
              fontSize: "100px",
              position: "absolute",
              bottom: 40,
              left: 20,
              color: "white",
              zIndex: "",
            }}
          >
            {unLock && "🔒"}
            {!unLock && "🔓"}
          </div>
        </Link>
      </div>
    </>
  );
}
