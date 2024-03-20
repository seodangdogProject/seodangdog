'use client';
// 모달을 띄우는 파일
import React, { useState, useCallback } from 'react';
import Image from 'next/image';
import styles from './page.module.css';
import landigStyles from './land.module.css';
import LoginModal from '../components/landingComponent/loginModal';
import LogoIcon from '../assets/landing-logo-icon.svg';
export default function Home() {
    const [isOpenModal, setOpenModal] = useState<boolean>(false);

    const onClickToggleModal = useCallback(() => {
        setOpenModal(!isOpenModal);
    }, [isOpenModal]);

    // 이미지들의 배열
    const images = [
        '/images/landing-main-image.png',
        '/images/landing-main2-image.png',
        '/images/landing-test-image.png',
        '/images/landing-wordlist-image.png',
        '/images/landing-quiz-image.png',
    ];

    // 반원 형태의 경로 생성
    const createSemiCirclePath = (
        radius: number,
        centerX: number,
        centerY: number,
        numberOfPoints: number
    ) => {
        const path = [];
        for (let i = 0; i < numberOfPoints; i++) {
            const angle = (Math.PI / numberOfPoints) * i;
            const x = centerX + radius * Math.cos(angle);
            const y = centerY + radius * Math.sin(angle);
            path.push(`${x},${y}`);
        }
        return path.join(' ');
    };

    // 이미지를 반원에 맞게 배치하는 JSX 생성
    const semiCircleImages = images.map((image, index) => {
        const radius = 150; // 반원의 반지름
        const numberOfImages = images.length;
        const centerX = 200; // 중심 X 좌표
        const centerY = 200; // 중심 Y 좌표
        const angleOffset = Math.PI / 2; // 시작 각도

        // 이미지의 각도 계산
        const angle = angleOffset + (Math.PI / (numberOfImages - 1)) * index;

        // 이미지의 좌표 계산
        const x = centerX + radius * Math.cos(angle);
        const y = centerY + radius * Math.sin(angle);

        return (
            <image
                key={index}
                href={image}
                x={x}
                y={y}
                width="50"
                height="50"
                style={{
                    transformOrigin: 'center center',
                    transform: `rotate(${angle * (180 / Math.PI)}deg)`,
                }}
            />
        );
    });

    return (
        <main className={landigStyles.main}>
            {isOpenModal && (
                <LoginModal
                    onClickToggleModal={onClickToggleModal}
                ></LoginModal>
            )}
            <div className={landigStyles.center_container}>
                <LogoIcon
                    style={{
                        marginBottom: '30px',
                    }}
                />
                <p className={landigStyles.description}>
                    뉴스 기사의 새로운 활용법
                </p>
                <div>
                    <button
                        className={landigStyles.rounded_button}
                        onClick={onClickToggleModal}
                    >
                        시작 하기
                    </button>
                </div>
            </div>
            <div className={landigStyles.bottom_news}>
                <Image
                    className={landigStyles.page_button}
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
                    className={landigStyles.page_button}
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
                    className={landigStyles.page_button}
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
                    className={landigStyles.page_button}
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
                    className={landigStyles.page_button}
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
