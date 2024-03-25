import React from "react";
import commonStyles from "./Section_common.module.css";
import Image from "next/image";
import Typewriter from "typewriter-effect";

interface ISectionProps {
    pageNum: number;
    window: Window;
    pageRefs: React.MutableRefObject<HTMLDivElement[]>;
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
                        marginRight: "40px",
                    }}
                >
                    <Typewriter
                        options={{
                            strings: ["여러분이", "관심 있어할"],
                            autoStart: true,
                        }}
                    />
                    <div className={commonStyles.title}>
                        여러분이{" "}
                        <span style={{ color: "rgba(151, 88, 255, 1)" }}>
                            관심 있어할
                        </span>{" "}
                        <br />
                        뉴스를 추천해드립니다
                    </div>
                    <div className={commonStyles.content}>
                        키워드별 추천과 비슷한 사용자 추천으로 <br />
                        폭넓은 추천을 제공합니다.
                    </div>
                </div>
                <Image
                    src="/images/landing-second.webp"
                    alt="secondImage"
                    width={600}
                    height={400}
                />
            </main>
        </div>
    );
};

export default Section;
