"use client";
import NewsContent from "@/components/newsDetail/NewsContent";
import styled from "./NewsDetailContainer.module.css";
import classNames from "classnames/bind";
import Quiz from "@/components/newsDetail/Quiz";
import Cover from "@/components/newsDetail/Cover";
import { useEffect, useState } from "react";
export default function NewsDetailContainer() {
  const [currentQuestion, setCurrentQuestion] = useState<number>(0);
  const cx = classNames.bind(styled);

  const [data, setData] = useState({});

  useEffect(() => {
    (async () => {
      const a = await fetch("https://j10e104.p.ssafy.io/api/news/11355", {
        method: "get",
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmltIiwiVVNFUiI6IlJPTEVfVVNFUiIsImV4cCI6MTcxMzU3ODcxNX0.6iCO_VO6jdC-fvfceiQtN6kyFqInb74AUBC-I4ZUYkg",
        },
      });
      const dt = await a.json();
      setData(dt);
    })();
  }, []);
  return (
    <>
      <div className={cx("container")}>
        <NewsContent data={data} />
        {currentQuestion === 0 ? (
          <Cover goNext={setCurrentQuestion} />
        ) : (
          <Quiz />
        )}
      </div>
    </>
  );
}
