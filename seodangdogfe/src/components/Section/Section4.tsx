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
                    "여러분의 <span style='color: #9758FF;'> 히스토리를</span><br/>"
                  )
                  .typeString("깔끔한 화면으로 보여줍니다.")
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
              뱃지의 획득여부를 확인하고 <br />
              대표 뱃지 이미지를 선택할 수 있습니다.
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
            src="/images/landing-main2-image.jpg"
            alt=""
          />
        )}
      </main>
    </div>
  );
};

export default Section;
