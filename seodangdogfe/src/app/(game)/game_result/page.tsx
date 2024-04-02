"use client";
// WordGame.tsx
import React, { useState, useEffect, useCallback } from "react";
import styles from "./gameresult_layout.module.css";
import { useRecoilState, RecoilRoot, useSetRecoilState } from "recoil";
import { useRouter } from "next/navigation";
import NextButton from "../../../assets/nextButton-icon.svg";
import WordDetailModal from "../../../components/wordComponent/wordDetailModal";
import {
    gameWordListState,
    correctWordListState,
    unCorrectWordListState,
    GameItem,
} from "../../../atoms/wordGame";
import { privateFetch } from "@/utils/http-commons";

const WordResult: React.FC = () => {
    const router = useRouter();
    const [isOpenModal, setOpenModal] = useState<boolean>(false);
    const setCorrectWordList = useSetRecoilState(correctWordListState);
    const setUnCorrectWordList = useSetRecoilState(unCorrectWordListState);
    const [correctWordList] = useRecoilState(correctWordListState);
    const [unCorrectWordList] = useRecoilState(unCorrectWordListState);
    const reqData = {
        wordSeq: correctWordList.map((item) => item.wordSeq),
    };
    const [clickedWord, setClickedWord] = useState<string | null>(null);

    const handleClick = () => {
        console.log("다음으로");

        (async () => {
            console.log(reqData);
            const res = await privateFetch("/game/result", "PATCH", reqData);
            if (res.status === 200) {
                // const data = await res.json();
                console.log("delete ok");
            } else {
                console.log("error 발생");
            }
        })();

        setCorrectWordList([]);
        setUnCorrectWordList([]);

        router.push("/word_game");
    };

    // 이동
    const handleOpenModal = (word: GameItem) => {
        setClickedWord((prevClickedWord) => word.word); // 함수형 업데이트 사용
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
    };

    return (
        <>
            <div className={styles.container}>
                {isOpenModal && (
                    <WordDetailModal
                        isOpen={isOpenModal}
                        onClose={handleCloseModal}
                        clickedWord={clickedWord}
                    ></WordDetailModal>
                )}
                <div className={styles.content_cotainer}>
                    <div className={styles.header}>SCORE</div>
                    <div className={styles.main_cotainer}>
                        <div className={styles.score}></div>
                        <div className={styles.left_container}>
                            {correctWordList.map((item, index) => (
                                <div
                                    key={item.wordSeq}
                                    className={styles.wordBox}
                                    style={{
                                        border: "2px solid blue",
                                    }}
                                    onClick={() => handleOpenModal(item)}
                                >
                                    {item.word}
                                </div>
                            ))}
                        </div>
                        <div className={styles.vertical_line}>
                            <div className={styles.circle}>
                                <span className={styles.score}>
                                    {correctWordList.length}
                                </span>
                                <span className={styles.per}>/</span>
                                <span className={styles.ten}>10</span>
                            </div>
                        </div>
                        <div className={styles.right_container}>
                            {unCorrectWordList.map((item, index) => (
                                <div
                                    key={item.wordSeq}
                                    className={styles.wordBox}
                                    style={{
                                        border: "2px solid red",
                                    }}
                                    onClick={() => handleOpenModal(item)}
                                >
                                    {item.word}
                                </div>
                            ))}
                        </div>
                    </div>
                    <div className={styles.footer}>
                        <div className={styles.des_text}>
                            맞힌 단어는 단어장에서 삭제됩니다
                        </div>
                        <NextButton
                            onClick={handleClick}
                            style={{ cursor: "pointer" }}
                        />
                    </div>
                </div>
            </div>
        </>
    );
};

export default WordResult;
