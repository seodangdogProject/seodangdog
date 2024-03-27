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
                {props.status && (
                    <Image
                        src="/images/landing-fifth.webp"
                        className={commonStyles.image}
                        alt="fifthImage"
                        width={620}
                        height={400}
                        style={{
                            marginRight: "40px",
                        }}
                    />
                )}

                <div className={commonStyles.description}>
                    {props.status && (
                        <Typewriter
                            onInit={(typewriter) => {
                                typewriter
                                    .typeString(
                                        "저장한 단어로 <br/> <span style='color: #9758FF;'>  퀴즈 </span>  를 풀어보세요!"
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
                            단어장에서 단어 10개를 선정해 <br />
                            스피드 퀴즈를 풀어볼 수 있습니다.{" "}
                        </div>
                    )}
                </div>
            </main>
        </div>
    );
};

export default Section;
