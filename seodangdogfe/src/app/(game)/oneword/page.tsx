'use client';
// WordGame.tsx
import React, { useState, useEffect, useCallback } from 'react';
import { useRecoilState, RecoilRoot } from 'recoil';
import { wordListState, Item } from '../../../atoms/wordGame';
import styles from './oneword_layout.module.css';
import Lottie from 'lottie-react';
import TimerIcon from '../../../assets/timer-icon.svg';

const OneWord: React.FC = () => {
    const [currentIndex, setCurrentIndex] = useState(0);
    const [wordList, setWordList] = useRecoilState(wordListState);
    const [testWord, setTestWord] = useState('김아림');
    const [inputValues, setInputValues] = useState(
        Array(testWord.length).fill('')
    ); // 입력된 문자열을 추적하는 상태
    // const [inputValues, setInputValues] = useState(
    //     Array(wordList[currentIndex].answer.length).fill('')
    // ); // 입력된 문자열을 추적하는 상태

    // 각 입력 요소에 대한 변경 핸들러
    const handleChange = (index: number, value: string) => {
        const newInputValues = [...inputValues]; // 이전 입력 값을 복사
        newInputValues[index] = value; // 변경된 값으로 업데이트
        setInputValues(newInputValues); // 변경된 입력 값을 상태에 저장

        // 모든 input이 채워졌는지 확인하여 자동으로 단어를 변환합니다.
        if (newInputValues.every((input) => input !== '')) {
            handleConvertWord();
        }
    };

    // 단어 변환 함수
    const handleConvertWord = () => {
        const convertedWord = inputValues.join(''); // 배열에 있는 문자들을 결합하여 단어로 변환
        console.log('Converted word:', convertedWord);
    };

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentIndex((prevIndex) => {
                if (prevIndex + 1 >= wordList.length) {
                    clearInterval(interval); // 배열의 끝에 도달하면 interval을 멈춥니다.
                }
                return prevIndex + 1;
            });
        }, 5000);

        return () => clearInterval(interval);
    }, [wordList.length]);

    return (
        <>
            <div className={styles.container}>
                <div className={styles.content_cotainer}>
                    <div className={styles.header_container}>스피드 퀴즈 !</div>
                    <div className={styles.count_container}>
                        <span> 1</span>
                        <span> /</span>
                        <span> 10</span>
                    </div>
                    <div className={styles.meaning_container}></div>
                    <div className={styles.answer_conatiner}>
                        {testWord.split('').map((char, index) => (
                            <div key={index} className={styles.characterBox}>
                                <input
                                    type="text"
                                    maxLength={1}
                                    value={inputValues[index]}
                                    onChange={(e) =>
                                        handleChange(index, e.target.value)
                                    }
                                />
                            </div>
                        ))}
                    </div>
                    <div className={styles.timer}>
                        <TimerIcon></TimerIcon>
                        <div className={styles.timer_bar}></div>
                    </div>
                </div>
            </div>
            <div>
                {/* <h1>{wordList[currentIndex].idx}</h1>
                <h1>{wordList[currentIndex].mean}</h1>
                <h1>{wordList[currentIndex].answer}</h1> */}
                {/* {wordList[currentIndex].answer.split('').map((char, index) => (
                    <div key={index} className={styles.characterBox}>
                        <input
                            type="text"
                            maxLength={1}
                            value={inputValues[index]}
                            onChange={(e) =>
                                handleChange(index, e.target.value)
                            }
                        />
                    </div>
                ))} */}
                <button
                    onClick={() =>
                        setCurrentIndex((prevIndex) => prevIndex + 1)
                    }
                >
                    다음 단어
                </button>
            </div>
        </>
    );
};

export default OneWord;
