import React from "react";
import commonStyles from "./Section_common.module.css";
import Image from "next/image";
import Typewriter from "typewriter-effect";

interface ISectionProps {
    pageNum: number;
    window: Window;
    pageRefs: React.MutableRefObject<HTMLDivElement[]>;
    status: boolean;
}

const Section = (props: ISectionProps) => {
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
            <main className={commonStyles.main}>
                <div
                    className={commonStyles.description}
                    style={{
                        marginRight: "20px",
                    }}
                >
                    {props.status && (
                        <Typewriter
                            onInit={(typewriter) => {
                                typewriter
                                    .typeString(
                                        "다시 보고 싶은 <br/> <span style='color: #9758FF;'> 단어를 저장 </span>  할 수 있습니다"
                                    )

                                    .pauseFor(1000)
                                    .start();
                            }}
                            options={{
                                wrapperClassName: `${commonStyles.title}`, // 커스텀 클래스 추가
                            }}
                        />
                    )}
                    {props.status && (
                        <div className={commonStyles.content}>
                            뉴스를 읽을 때 <br />
                            단어를 스크랩하여 <br />
                            나만의 단어장에 저장할 수 있습니다.
                        </div>
                    )}
                </div>
                {props.status && (
                    <Image
                        src="/images/landing-fourth.webp"
                        className={commonStyles.image}
                        alt="secondImage"
                        width={620}
                        height={400}
                    />
                )}
            </main>
        </div>
    );
};

export default Section;
