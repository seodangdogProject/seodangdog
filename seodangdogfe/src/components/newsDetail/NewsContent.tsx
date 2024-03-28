/* eslint-disable react/no-unescaped-entities */
import styled from "./NewsContent.module.css";
import classNames from "classnames/bind";
export default function NewsContent({
  keywords,
  data,
}: {
  keywords: string[];
  data: any;
}) {
  const cx = classNames.bind(styled);
  return (
    <>
      <section className={cx("news-content")}>
        <h1 className={cx("title")}>{data.newsTitle}</h1>
        <div className={cx("date")}>2024.03.06. 오전 11:37</div>
        <div className={cx("hashtag")}>
          {keywords.map((item) => (
            <div key={item} className={cx("hashtag-item")}>
              {item}
            </div>
          ))}
        </div>
        <div className={cx("content")}>
          <div className={cx("img")}>
            <img src={data.newsImgUrl} alt="" />
          </div>
          <pre className={cx("text")}>{data.newsMainText}</pre>
        </div>
      </section>
    </>
  );
}
