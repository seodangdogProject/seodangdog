"use client";
import Buttons from "@/components/Buttons";
import Section1 from "@/components/Section/Section1";
import Section2 from "@/components/Section/Section2";
import Section3 from "@/components/Section/Section3";
import Section4 from "@/components/Section/Section6";
import Section5 from "@/components/Section/Section5";
import { useEffect, useRef, useState } from "react";
import { pageObjArray } from "./page";

export const Landing = () => {
  const [windowObj, setWindowObj] = useState<Window>();
  const [currentPageNum, setCurrentPageNum] = useState<number>(1);
  const totalNum = pageObjArray.length;
  const pageRefs = useRef<HTMLDivElement[]>([]);
  const [status, setStatus] = useState<boolean[]>([
    true,
    false,
    false,
    false,
    false,
  ]);

  useEffect(() => {
    if (window !== undefined) {
      setWindowObj(window);
    }
  }, []);

  // 페이지 변경 함수
  const handlePageChange = (event: Event) => {
    let scroll = windowObj?.scrollY!;
    for (let i = 1; i <= totalNum; i++) {
      // 스크롤이 해당 섹션에 진입했는지 판단 && 해당 스크롤이 해당 섹션에 머물러 있는지
      if (
        scroll > pageRefs.current[i].offsetTop - windowObj!.outerHeight / 3 &&
        scroll <
          pageRefs.current[i].offsetTop -
            windowObj!.outerHeight / 3 +
            pageRefs.current[i].offsetHeight
      ) {
        setCurrentPageNum(i);
        setPageStatus(i);
        break;
      }
    }
  };

  const setPageStatus = (i: number) => {
    const newArr = [false, false, false, false, false];
    for (let idx = 0; idx <= totalNum; idx++) {
      if (i == idx + 1) newArr[idx] = true;
    }

    setStatus(newArr);
  };

  // 버튼 클릭
  const handlePointClick = (pageNum: number) => {
    windowObj?.scrollTo({
      top: pageRefs.current[pageNum].offsetTop,
      behavior: "smooth",
    });
  };

  useEffect(() => {
    windowObj?.addEventListener("scroll", handlePageChange);
    return () => {
      windowObj?.removeEventListener("scroll", handlePageChange);
    };
  }, [windowObj]);

  return (
    <>
      <main style={{ position: "relative", overflow: "hidden" }}>
        <Section1
          key={1}
          pageNum={1}
          window={windowObj!}
          pageRefs={pageRefs}
          status={status[0]}
        />
        <Section2
          key={2}
          pageNum={2}
          window={windowObj!}
          pageRefs={pageRefs}
          status={status[1]}
        />
        <Section3
          key={3}
          pageNum={3}
          window={windowObj!}
          pageRefs={pageRefs}
          status={status[2]}
        />
        <Section5
          key={6}
          pageNum={6}
          window={windowObj!}
          pageRefs={pageRefs}
          status={status[5]}
        />
        <Section4
          key={4}
          pageNum={4}
          window={windowObj!}
          pageRefs={pageRefs}
          status={status[3]}
        />
        <Section5
          key={5}
          pageNum={5}
          window={windowObj!}
          pageRefs={pageRefs}
          status={status[4]}
        />

        <div
          style={{
            position: "fixed", // fixed
            display: "flex", // flex
            flexDirection: "column", // flex-col
            gap: "1rem", // space-y-4
            top: "20rem", // top-96
            right: "2.5rem", // right-10
            zIndex: 999, // z-10
          }}
        >
          <Buttons
            pageObjArray={pageObjArray}
            currentPageNum={currentPageNum}
            handlePointClick={handlePointClick}
          />
        </div>
      </main>
    </>
  );
};
