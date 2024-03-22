"use client";
import React, { useState, useEffect, useCallback } from "react";
import { useRouter } from "next/navigation";
import { useRecoilState, RecoilRoot } from "recoil";
import { userKeywords } from "../../../atoms/joinRecoil";
import Link from "next/link";
import styles from "./game_layout.module.css";
import JoinModal from "../../../components/joinComponent/joinModal";

interface keyword {
    id: number;
    keyword: string;
}

export default function Join() {
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
    ];

    const router = useRouter();
    const [letters, setLetters] = useState<keyword[]>([]);
    const [isOpenModal, setOpenModal] = useState<boolean>(false);
    const [userKeywords, setUserKeywords] = useState<
        { id: number; keyword: string }[]
    >([]);
    // 클릭 여부를 추적하는 상태 추가
    const [clickedLetters, setClickedLetters] = useState<number[]>([]);
    const [unLock, setUnLock] = useState(true);
    const [userKeywordsSize, setUserKeywordsSize] = useState(0);

    function lockToggle() {
        setUnLock(false);
    }

    const onClickToggleModal = useCallback(() => {
        setOpenModal(!isOpenModal);
    }, [isOpenModal]);

    const handleLetterClick = async (id: number, keyword: string) => {
        console.log(keyword, " 잡음");
        setUserKeywords((prevKeywords) => [...prevKeywords, { id, keyword }]);
        setUserKeywordsSize((count) => count + 1);
        console.log(userKeywordsSize);

        if (userKeywordsSize >= 9) {
            lockToggle();
        }

        // 클릭된 단어의 id를 추적하는 상태 업데이트
        setClickedLetters((prevClicked) => [...prevClicked, id]);
    };
    useEffect(() => {
        setLetters(keywordList);
    }, []);

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
                                clickedLetters.includes(item.id)
                                    ? styles.clicked
                                    : ""
                            }`}
                            onClick={() =>
                                handleLetterClick(item.id, item.keyword)
                            }
                        >
                            {item.keyword}
                        </div>
                    ))}
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
