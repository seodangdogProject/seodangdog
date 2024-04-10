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
            marginRight: "40px",
          }}
        >
          {props.status && (
            <Typewriter
              onInit={(typewriter) => {
                typewriter
                  .typeString(
                    "여러분이 <span style='color: #9758FF;'> 관심 있어할</span><br/>"
                  )
                  .typeString("뉴스를 추천해드립니다")
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
              키워드별 추천과 비슷한 사용자 추천으로 <br />
              폭넓은 추천을 제공합니다.
            </div>
          )}
        </div>
        {props.status && (
          //   <Image
          //     src="/images/landing-main-image.jpg"
          //     className={commonStyles.image}
          //     alt="secondImage"
          //     width={620}
          //     height={348.75}
          //   />
          <img
            className={commonStyles.image}
            style={{
              width: "620px",
            }}
            src="/images/landing-main-image.jpg"
            alt=""
          />
        )}
      </main>
    </div>
  );
};

export default Section;
