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
                <div
                    className={commonStyles.description}
                    style={{
                        marginRight: "20px",
                    }}
                >
                    <div
                        className={commonStyles.title}
                        style={{ fontSize: "60px" }}
                    >
                        다시 보고 싶은
                        <br />
                        <span style={{ color: "rgba(151, 88, 255, 1)" }}>
                            단어를 저장
                        </span>
                        할 수 있습니다
                    </div>
                    <div className={commonStyles.content}>
                        뉴스를 읽을 때 <br />
                        단어를 스크랩하여 <br />
                        나만의 단어장에 저장할 수 있습니다.
                    </div>
                </div>
                <Image
                    src="/images/landing-fourth.webp"
                    alt="secondImage"
                    width={600}
                    height={400}
                />
            </main>
        </div>
    );
};

export default Section;
