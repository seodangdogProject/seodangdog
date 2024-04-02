import React, { useState, useCallback, useEffect } from "react";
import Image from "next/image";
import landingStyles from "./Section1.module.css";
import LoginModal from "../landingComponent/loginModal";
import LogoIcon from "../../assets/landing-logo-icon.svg";

interface ISectionProps {
    pageNum: number;
    window: Window;
    pageRefs: React.MutableRefObject<HTMLDivElement[]>;
    status: boolean;
}

const Section = (props: ISectionProps) => {
    const [isOpenModal, setOpenModal] = useState<boolean>(false);

    const onClickToggleModal = useCallback(() => {
        setOpenModal(!isOpenModal);
    }, [isOpenModal]);

    return (
        <div
            ref={(element) => {
                props.pageRefs.current[props.pageNum] = element!;
            }}
            style={{
                width: "100vw",
                height: "100vh",
                overflowX: "hidden",
            }}
        >
            <main className={landingStyles.main}>
                {isOpenModal && (
                    <LoginModal
                        onClickToggleModal={onClickToggleModal}
                    ></LoginModal>
                )}
                <div className={landingStyles.center_container}>
                    <LogoIcon
                        style={{
                            marginBottom: "30px",
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
                            시작하기
                        </button>
                    </div>
                </div>
                <div className={landingStyles.bottom_news}>
                    <Image
                        className={landingStyles.page_button}
                        src="/images/landing-main-image.webp"
                        alt="My Image"
                        width={300}
                        height={200.25}
                        style={{
                            position: "absolute",
                            marginTop: "40px",
                            top: "70px",
                            left: "100px",
                        }}
                    />
                    <Image
                        className={landingStyles.page_button}
                        src="/images/landing-main2-image.webp"
                        alt="My Image"
                        width={300}
                        height={200.25}
                        style={{
                            position: "absolute",
                            top: "50px",
                            left: "350px",
                        }}
                    />
                    <Image
                        className={landingStyles.page_button}
                        src="/images/landing-test-image.webp"
                        alt="My Image"
                        width={300}
                        height={200.25}
                        style={{
                            position: "absolute",
                            top: "40px",
                            left: "600px",
                        }}
                    />
                    <Image
                        className={landingStyles.page_button}
                        src="/images/landing-wordlist-image.webp"
                        alt="My Image"
                        width={300}
                        height={200.25}
                        style={{
                            position: "absolute",
                            top: "50px",
                            left: "850px",
                        }}
                    />
                    <Image
                        className={landingStyles.page_button}
                        src="/images/landing-quiz-image.webp"
                        alt="My Image"
                        width={300}
                        height={200.25}
                        style={{
                            position: "absolute",
                            marginTop: "40px",
                            top: "70px",
                            left: "1100px",
                        }}
                    />
                </div>
            </main>
        </div>
    );
};

export default Section;
