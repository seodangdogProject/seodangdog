import NewsContent from "@/components/newsDetail/NewsContent";
import styled from "./NewsDetailContainer.module.css";
import classNames from "classnames/bind";
import Quiz from "@/components/newsDetail/Quiz";
import Cover from "@/components/newsDetail/Cover";
export default function NewsDetailContainer() {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={cx("container")}>
        <NewsContent />
        {/* <Cover /> */}
        <Quiz />
      </div>
    </>
  );
}
