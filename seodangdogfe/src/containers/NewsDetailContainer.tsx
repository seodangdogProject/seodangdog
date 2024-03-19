"use client";
import NewsContent from "@/components/newsDetail/NewsContent";
import styled from "./NewsDetailContainer.module.css";
import classNames from "classnames/bind";
import Quiz from "@/components/newsDetail/Quiz";
import Cover from "@/components/newsDetail/Cover";
import { useState } from "react";
export default function NewsDetailContainer() {
  const [currentQuestion, setCurrentQuestion] = useState<number>(0);
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={cx("container")}>
        <NewsContent />
        {currentQuestion === 0 ? (
          <Cover goNext={setCurrentQuestion} />
        ) : (
          <Quiz />
        )}
      </div>
    </>
  );
}
