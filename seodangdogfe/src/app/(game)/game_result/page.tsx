'use client';
// WordGame.tsx
import React, { useState, useEffect, useCallback } from 'react';
import styles from './gameresult_layout.module.css';
import { useRecoilState, RecoilRoot } from 'recoil';
import {
    correctWordListState,
    unCorrectWordListState,
    Item,
} from '../../../atoms/wordGame';

import NextButton from '../../../assets/nextButton-icon.svg';
const WordResult: React.FC = () => {
    const [correctWordList, setCorrectWordList] =
        useRecoilState(correctWordListState);
    const [unCorrectWordList, setUnCorrectWordList] = useRecoilState(
        unCorrectWordListState
    );

    return (
        <>
            <div className={styles.container}>
                <div className={styles.content_cotainer}>
                    <div className={styles.header}>SCORE</div>
                    <div className={styles.main_cotainer}>
                        <div className={styles.score}></div>
                        <div className={styles.left_container}>
                            {correctWordList.map((item, index) => (
                                <div key={item.idx} className={styles.wordBox}>
                                    {item.answer}
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
                                <div key={item.idx} className={styles.wordBox}>
                                    {item.answer}
                                </div>
                            ))}
                        </div>
                    </div>
                    <div className={styles.footer}>
                        <div className={styles.des_text}>
                            맞힌 단어는 단어장에서 삭제됩니다
                        </div>
                        <NextButton></NextButton>
                    </div>
                </div>
            </div>
        </>
    );
};

export default WordResult;
