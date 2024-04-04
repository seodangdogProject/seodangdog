import React, { useState } from "react";
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
          //   <Image
          //     src="/images/landing-third.webp"
          //     className={commonStyles.image}
          //     alt="thirdImage"
          //     width={620}
          //     height={400}
          //     style={{
          //       marginRight: "40px",
          //     }}
          //   />
          <img
            className={commonStyles.image}
            style={{
              width: "620px",
              marginRight: "40px",
            }}
            src="/images/landing-test-image.jpg"
            alt=""
          />
        )}

        <div className={commonStyles.description}>
          {props.status && (
            <Typewriter
              onInit={(typewriter) => {
                typewriter
                  .typeString(
                    "뉴스의 내용으로 <br/> <span style='color: #9758FF;'> 문제를 제공</span>합니다"
                  )
                  .start();
              }}
              options={{
                cursor: ` `,
                wrapperClassName: `${commonStyles.title}`, // 커스텀 클래스 추가
              }}
            />
          )}
          {props.status && (
            <div className={commonStyles.content}>
              뉴스의 내용으로 <br /> 어휘, 판단, 추론, 요약까지
              <br /> 네 가지 문제를 풀어볼 수 있습니다.
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default Section;
