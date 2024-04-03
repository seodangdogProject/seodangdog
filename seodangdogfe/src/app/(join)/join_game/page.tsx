"use client";
import React, { useState, useEffect, useCallback } from "react";
import { useRouter } from "next/navigation";
import { useRecoilState, RecoilRoot } from "recoil";
import { userKeywords } from "../../../atoms/joinRecoil";
import Link from "next/link";
import styles from "./game_layout.module.css";
import JoinModal from "../../../components/joinComponent/joinModal";
import { publicFetch } from "../../../utils/http-commons";
import ToastPopup from "@/components/toast/Toast";

interface Keyword {
  id: number;
  keyword: string;
}

export default function Join() {
  const router = useRouter();
  const [letters, setLetters] = useState<Keyword[]>([]);
  const [isOpenModal, setOpenModal] = useState<boolean>(false);
  const [userKeywords, setUserKeywords] = useState<
    { id: number; keyword: string }[]
  >([]);
  const [clickedKeywords, setClickedKeywords] = useState<string[]>([]);
  const [unLock, setUnLock] = useState(true);
  const [userKeywordsSize, setUserKeywordsSize] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [allKeywords, setAllKeywords] = useState<Keyword[]>([]);

  const [toast, setToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");

  //배열 섞기
  function shuffleArray(array: Keyword[]) {
    for (let i = array.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
  }
  //배열 분할
  function chunkArray(array: Keyword[], size: number) {
    const result = [];
    for (let i = 0; i < array.length; i += size) {
      result.push(array.slice(i, i + size));
    }
    return result;
  }

  function lockToggle() {
    setUnLock(false);
  }

  //키워드 추출
  useEffect(() => {
    const fetchKeywords = async () => {
      try {
        const response = await publicFetch("/keyword/join", "GET");
        // if (!response.ok) throw new Error("Failed to fetch keywords");
        const data = await response.json();
        // console.log("Received data:", data);

        let keywordList: Keyword[] = data.map((item: any) => ({
          id: item.joinKeywordSeq,
          keyword: item.keyword,
        }));

        keywordList = shuffleArray(keywordList);
        setAllKeywords(keywordList);
      } catch (error) {
        console.error("Error fetching keywords:", error);
      }
    };

    fetchKeywords();
  }, []);

  //키워드 20개씩 조회
  useEffect(() => {
    const chunkedKeywords = chunkArray(allKeywords, 30);
    if (currentPage < chunkedKeywords.length) {
      setLetters(chunkedKeywords[currentPage]);
    }
  }, [currentPage, allKeywords]);

  const nextPage = useCallback(() => {
    setCurrentPage((prevPage) => prevPage + 1);
  }, []);

  const onClickToggleModal = useCallback(() => {
    if (userKeywordsSize > 9) {
      setOpenModal(!isOpenModal);
    } else {
      setToastMessage("단어를 10개 이상 선택해주세요.");
      setToast(true);
    }
  }, [isOpenModal, userKeywordsSize]);

  const handleLetterClick = async (id: number, keyword: string) => {
    // 이미 선택된 단어인지 확인
    const isAlreadySelected = userKeywords.some(
      (item) => item.keyword === keyword
    );
    if (isAlreadySelected) {
      // 이미 선택된 단어라면, 리스트에서 제거
      setUserKeywords(userKeywords.filter((item) => item.keyword !== keyword));
      //   console.log(keyword, " 제거");
      setUserKeywordsSize((count) => count - 1);
      setClickedKeywords(
        clickedKeywords.filter((existingKeyword) => existingKeyword !== keyword)
      );
    } else {
      // 선택되지 않은 새로운 단어라면, 리스트에 추가
      setUserKeywords((prevKeywords) => [...prevKeywords, { id, keyword }]);
      setClickedKeywords([...clickedKeywords, keyword]);
      //   console.log(keyword, " 잡음");
      setUserKeywordsSize((count) => count + 1);
    }
  };
  //담긴 키워드 목록 확인
  //   useEffect(() => {
  //     console.log("현재 선택된 키워드 목록:", userKeywords);
  //   }, [userKeywords]);

  useEffect(() => {
    setUserKeywordsSize(userKeywords.length);
    setUnLock(userKeywords.length < 10);
  }, [userKeywords]);

  useEffect(() => {
    console.log("클릭된 키워드 목록:", clickedKeywords);
  }, [clickedKeywords]);

  useEffect(() => {
    setUserKeywordsSize(userKeywords.length);
    console.log("현재 선택된 키워드 개수:", userKeywords.length);
  }, [userKeywords]);

  return (
    <>
      <div
        className={`${styles.stage_bg} ${styles.mouse}`}
        style={{
          width: "100%",
          height: "100vh",
          backgroundSize: "cover", // 이미지가 요소에 맞게 자동으로 조절되도록 cover 값을 설정합니다.
          backgroundPosition: "center", // 이미지를 가운데 정렬합니다.
        }}
      >
        <div className={styles.titleContainer}>
          좋아하는 키워드를 선택하세요.
        </div>
        {isOpenModal && (
          <JoinModal
            data={userKeywords}
            onClickToggleModal={onClickToggleModal}
          ></JoinModal>
        )}

        <div className={styles.wordContainer}>
          {letters.map((item, index) => (
            <div
              key={item.id}
              className={`${styles.wordBox} ${
                clickedKeywords.includes(item.keyword) ? styles.clicked : ""
              }`}
              //   style={getRandomPosition()} // 랜덤 위치 적용
              onClick={() => handleLetterClick(item.id, item.keyword)}
            >
              {item.keyword}
            </div>
          ))}
        </div>

        <button
          onClick={nextPage}
          className={`${styles.buttonNewKeywords} ${styles.mouse}`}
          style={{ top: "50px", right: "30px" }}
        >
          새로운 키워드 받기
        </button>

        <div>
          {toast && <ToastPopup setToast={setToast} message={toastMessage} />}
        </div>

        <Link href="/join_game">
          <div
            onClick={onClickToggleModal}
            style={{
              fontSize: "80px",
              position: "absolute",
              bottom: "50px",
              right: "80px",
              color: "white",
              zIndex: 1,
            }}
            className={`${styles.mouse} ${
              unLock ? styles.lock : styles.unlock
            }`}
          >
            {unLock && "🔒"}
            {!unLock && "🔓"}
          </div>
          <button
            onClick={onClickToggleModal}
            className={`${styles.nextButton} ${styles.mouse} ${
              userKeywordsSize >= 10 ? styles.active : ""
            }`}
          >
            다음
          </button>
        </Link>

        <div className={styles.selectedWordCount}>
          선택한 키워드 개수: {userKeywordsSize}
        </div>
        <div className={styles.minimumKeywordAlert}>
          키워드를 10개 이상 선택해주세요.
        </div>
      </div>
    </>
  );
}
