'use client';
// 모달을 띄우는 파일
import React, { useState, useCallback } from 'react';
import Image from 'next/image';
import styles from './page.module.css';
import landigStyles from './land.module.css';
import LoginModal from '../components/landingComponent/loginModal';

export default function Home() {
    const [isOpenModal, setOpenModal] = useState<boolean>(false);

    const onClickToggleModal = useCallback(() => {
        setOpenModal(!isOpenModal);
    }, [isOpenModal]);

    return (
        <main className={landigStyles.main}>
            {isOpenModal && (
                <LoginModal
                    onClickToggleModal={onClickToggleModal}
                ></LoginModal>
            )}
            <div className={landigStyles.center_container}>
                <p className={landigStyles.description}>
                    뉴스 기사의 새로운 활용법
                </p>
                <div>
                    <button onClick={onClickToggleModal}>시작 하기</button>
                </div>
            </div>
        </main>
    );
}
