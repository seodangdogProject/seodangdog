import changeDateFormat from "@/utils/changeDateFormat";
import styled from "../NewsContent.module.css";
import classNames from "classnames/bind";
export default function NewsContent({
  data,
  keywords,
}: {
  data: any;
  keywords: string[];
}) {
  const cx = classNames.bind(styled);
  return (
    <>
      {data && (
        <section className={cx("news-content")}>
          <h1 className={cx("title")}>{data.newsTitle}</h1>
          <div className={cx("date")}>
            {changeDateFormat(data.newsCreatedAt)}
          </div>
          <div className={cx("hashtag")}>
            {keywords.map((item) => (
              <div key={item} className={cx("hashtag-item")}>
                # {item}
              </div>
            ))}
          </div>
          <div className={cx("content")}>
            <div className={cx("img")}>
              <img src={data.newsImgUrl} alt="" />
            </div>
            {/* <pre className={cx("text")}>{data.newsMainText}</pre> */}
            <pre className={cx("text", ["red"])}>
              {data.newsPos.map(
                (item: { word: string; pos: string }, index: number) => {
                  const { word, pos } = item;
                  // 뛰어 쓰기일 경우
                  if (pos === "Foreign" && word === "\n\n") {
                    return (
                      <div key={index} data-highlight-idx={index}>
                        <br />
                        <br />
                      </div>
                    );
                    // 단어일 경우
                  } else if (pos === "Noun" || pos === "Alpha") {
                    return (
                      <span
                        key={index}
                        className={cx({
                          filled:
                            data.highlightList !== null
                              ? data.highlightList.includes(index)
                              : false,
                          "word-circle":
                            data.wordList !== null
                              ? data.wordList.includes(index)
                              : false,
                        })}
                        data-highlight-idx={index}
                      >
                        {word}
                      </span>
                    );
                  } else {
                    return (
                      <span
                        key={index}
                        className={cx({
                          filled:
                            data.highlightList !== null
                              ? data.highlightList.includes(index)
                              : false,
                        })}
                        data-highlight-idx={index}
                      >
                        {word}
                      </span>
                    );
                  }
                }
              )}
            </pre>
          </div>
        </section>
      )}
    </>
  );
}
