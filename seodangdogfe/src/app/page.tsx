'use client';
// 모달을 띄우는 파일
import React, { useState, useCallback } from 'react';
import Image from 'next/image';
import styles from './page.module.css';
import landingStyles from './land.module.css';
import LoginModal from '../components/landingComponent/loginModal';
import LogoIcon from '../assets/landing-logo-icon.svg';
export default function Home() {
    const [isOpenModal, setOpenModal] = useState<boolean>(false);

    const onClickToggleModal = useCallback(() => {
        setOpenModal(!isOpenModal);
    }, [isOpenModal]);

    return (
        <main className={landingStyles.main}>
            {isOpenModal && (
                <LoginModal
                    onClickToggleModal={onClickToggleModal}
                ></LoginModal>
            )}
            <div className={landingStyles.center_container}>
                <LogoIcon
                    style={{
                        marginBottom: '30px',
                    }}
                />
                <p className={landingStyles.description}>
                    뉴스 기사의 새로운 활용법
                </p>
                <div>
                    <button
                        className={landingStyles.rounded_button}
                        onClick={onClickToggleModal}
                    >
                        시작 하기
                    </button>
                </div>
            </div>
            <div className={landingStyles.bottom_news}>
                <Image
                    className={landingStyles.page_button}
                    src="/images/landing-main-image.png"
                    alt="My Image"
                    width={500}
                    height={281.25}
                    style={{
                        position: 'absolute',
                        marginTop: '40px',
                        top: '170px',
                        left: '100px',
                    }}
                />
                <Image
                    className={landingStyles.page_button}
                    src="/images/landing-main2-image.png"
                    alt="My Image"
                    width={500}
                    height={281.25}
                    style={{
                        position: 'absolute',
                        top: '150px',
                        left: '400px',
                    }}
                />
                <Image
                    className={landingStyles.page_button}
                    src="/images/landing-test-image.png"
                    alt="My Image"
                    width={500}
                    height={281.25}
                    style={{
                        position: 'absolute',
                        top: '130px',
                        left: '700px',
                    }}
                />
                <Image
                    className={landingStyles.page_button}
                    src="/images/landing-wordlist-image.png"
                    alt="My Image"
                    width={500}
                    height={281.25}
                    style={{
                        position: 'absolute',
                        top: '150px',
                        left: '1000px',
                    }}
                />
                <Image
                    className={landingStyles.page_button}
                    src="/images/landing-quiz-image.png"
                    alt="My Image"
                    width={500}
                    height={281.25}
                    style={{
                        position: 'absolute',
                        marginTop: '40px',
                        top: '170px',
                        left: '1300px',
                    }}
                />
            </div>
        </main>
    );
}
