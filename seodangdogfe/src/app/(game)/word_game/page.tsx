'use client';
import React, { useState, useEffect, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import { useRecoilState, RecoilRoot } from 'recoil';
import styles from './wordgame_layout.module.css';
import GameLogo from '../../../assets/wordgame-logo-icon.svg';

export default function WordGame() {
    const router = useRouter();
    const [isAvailable, setIsAvailable] = useState<boolean>(false); // api 요청으로 받아오기
    const [count, setCount] = useState<number>(0); // api 요청으로 받아오기

    return (
        <>
            <div className={styles.container}>
                <div className={styles.content_cotainer}>
                    <GameLogo className={styles.logo} />
                    <div className={styles.word_count_box}>
                        {!isAvailable && (
                            <>
                                <span className={styles.word_count}>
                                    {count}
                                </span>
                            </>
                        )}
                        {isAvailable && (
                            <>
                                <span className={styles.word_count}>10+</span>
                            </>
                        )}

                        <span> /</span>
                        <span> 10</span>
                    </div>
                    {!isAvailable && (
                        <>
                            <div className={styles.des_container}>
                                <div
                                    style={{
                                        color: 'red',
                                        marginBottom: '5px',
                                    }}
                                >
                                    저장한 단어의 개수가 부족합니다.
                                </div>
                                <div style={{ color: 'red' }}>
                                    뉴스 기사에서 단어를 저장해주세요!
                                </div>
                            </div>
                        </>
                    )}
                    {isAvailable && (
                        <>
                            <div className={styles.des_container}>
                                <div
                                    style={{
                                        color: 'rgba(88, 104, 255, 1)',
                                        marginBottom: '5px',
                                    }}
                                >
                                    단어장의 단어가 충분합니다!
                                </div>
                                <div style={{ color: 'rgba(88, 104, 255, 1)' }}>
                                    10개를 선정해 스피드 퀴즈를 진행합니다!
                                </div>
                            </div>
                        </>
                    )}

                    <div className={styles.game_start_btn}>시작</div>
                    <div className={styles.footer}>
                        맞힌 단어는 단어장에서 삭제됩니다.
                    </div>
                </div>
            </div>
        </>
    );
}
