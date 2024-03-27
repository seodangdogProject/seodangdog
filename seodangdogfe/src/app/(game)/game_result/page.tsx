"use client";
// WordGame.tsx
import React, { useState, useEffect, useCallback } from "react";
import styles from "./gameresult_layout.module.css";
import { useRecoilState, RecoilRoot, useSetRecoilState } from "recoil";
import { useRouter } from "next/navigation";
import NextButton from "../../../assets/nextButton-icon.svg";
import {
    gameWordListState,
    correctWordListState,
    unCorrectWordListState,
    Item,
} from "../../../atoms/wordGame";
import { privateFetch } from "@/utils/http-commons";

const WordResult: React.FC = () => {
    const router = useRouter();
    const setCorrectWordList = useSetRecoilState(correctWordListState);
    const setUnCorrectWordList = useSetRecoilState(unCorrectWordListState);
    const [correctWordList] = useRecoilState(correctWordListState);
    const [unCorrectWordList] = useRecoilState(unCorrectWordListState);
    const reqData = {
        wordSeq: unCorrectWordList.map((item) => item.wordSeq),
    };
    const handleClick = () => {
        console.log("다음으로");

        (async () => {
            console.log(reqData);
            const res = await privateFetch("/game/result", "PATCH", reqData);
            if (res.status === 200) {
                const data = await res.json();
                console.log(data);
            } else {
                console.log("error 발생");
            }
        })();

        setCorrectWordList([]);
        setUnCorrectWordList([]);

        router.push("/word_game");
    };

    return (
        <>
            <div className={styles.container}>
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
                                        border: "1px solid blue",
                                    }}
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
                                        border: "1px solid red",
                                    }}
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
                        <NextButton onClick={handleClick} />
                    </div>
                </div>
            </div>
        </>
    );
};

export default WordResult;
