import React from "react";
import commonStyles from "./Section_common.module.css";
import Image from "next/image";

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
                <Image
                    src="/images/landing-third.webp"
                    alt="thirdImage"
                    width={600}
                    height={400}
                    style={{
                        marginRight: "40px",
                    }}
                />
                <div className={commonStyles.description}>
                    <div className={commonStyles.title}>
                        뉴스의 내용으로
                        <br />{" "}
                        <span style={{ color: "rgba(151, 88, 255, 1)" }}>
                            문제를 제공
                        </span>{" "}
                        합니다
                    </div>
                    <div className={commonStyles.content}>
                        뉴스의 내용으로 <br /> 어휘, 판단, 추론, 요약까지
                        <br /> 네 가지 문제를 풀어볼 수 있습니다.
                    </div>
                </div>
            </main>
        </div>
    );
};

export default Section;
