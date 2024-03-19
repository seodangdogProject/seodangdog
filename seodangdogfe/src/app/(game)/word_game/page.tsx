'use client';
import React, { useState, useEffect, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import { useRecoilState, RecoilRoot } from 'recoil';
import styles from './wordgame_layout.module.css';
import GameLogo from '../../../assets/wordgame-logo-icon.svg';
import { gameWordListState, Item } from '../../../atoms/wordGame';
import Link from 'next/link';

export default function WordGame() {
    const router = useRouter();
    const [isAvailable, setIsAvailable] = useState<boolean>(true); // api 요청으로 받아오기
    const [count, setCount] = useState<number>(0); // api 요청으로 받아오기
    const [wordList, setWordList] = useRecoilState(gameWordListState);

    // async function fetchWordList(): Promise<Item[]> {
    //     // 데이터를 받아오는 비동기 작업을 수행합니다. 예를 들어, API 호출을 수행합니다.
    //     const response = await fetch('https://api.example.com/word-list');
    //     const data = await response.json();
    //     return data.wordList; // 데이터에서 원하는 형태로 추출하여 반환합니다.
    // }

    useEffect(() => {
        const fetchData = async () => {
            try {
                //const data = await fetchWordList();
                const data = [{ idx: 1, answer: '정답', mean: '뜻' }];
                setWordList(data); // 여기서 setWordList에 전달되는 값이 올바른지 확인해야 합니다.
            } catch (error) {
                console.error('Error fetching word list:', error);
            }
        };

        fetchData();
    }, [setWordList]);

    return (
        <>
            <div className={styles.container}>
                <div className={styles.content_cotainer}>
                    <GameLogo className={styles.logo} />
                    <div className={styles.word_count_box}>
                        {!isAvailable && (
                            <>
                                <span
                                    className={styles.word_count}
                                    style={{ color: 'red' }}
                                >
                                    {count}
                                </span>
                            </>
                        )}
                        {isAvailable && (
                            <>
                                <span
                                    className={styles.word_count}
                                    style={{ color: 'rgba(88, 104, 255, 1)' }}
                                >
                                    10+
                                </span>
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

                    {isAvailable && (
                        <Link href="/oneword">
                            <div className={styles.game_start_btn}>시작</div>
                        </Link>
                    )}
                    {!isAvailable && (
                        <Link href="/oneword">
                            <div
                                className={styles.game_start_btn}
                                style={{
                                    color: 'rgba(180, 188, 255, 1)',
                                    cursor: 'none',
                                }}
                            >
                                시작
                            </div>
                        </Link>
                    )}
                    <div className={styles.footer}>
                        <div className={styles.footer_text}>
                            맞힌 단어는 단어장에서 삭제됩니다.
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
