/* eslint-disable react/no-unescaped-entities */
import { useEffect, useRef, useState } from "react";
import styled from "./NewsContent.module.css";
import classNames from "classnames/bind";
export default function NewsContent({
  keywords,
  data,
  cursor,
}: {
  keywords: string[];
  data: any;
  cursor?: number | null;
}) {
  const cx = classNames.bind(styled);
  const textEl = useRef<HTMLPreElement>(null);
  const selection = useRef(window.getSelection());
  const [startIdx, setStartIdx] = useState<number>(0);
  const [highlightList, setHighLightList] = useState<number[]>([]);

  useEffect(() => {
    setHighLightList(data?.highlightList || []);
  }, []);

  // 형광팬 칠하고 지우는메서드
  function highlightEnter(e: any) {
    if (cursor === 0 || cursor === 1)
      setStartIdx(Number(e.target.dataset["highlightIdx"]));
  }
  function highlightOut(e: any) {
    // 커서가 형광팬이라면
    if (cursor === 0) {
      const endIdx: number = e.target.dataset["highlightIdx"];
      let list = new Set<number>(highlightList);
      for (let index = startIdx; index <= endIdx; index++) {
        list.add(index);
      }
      setHighLightList(Array.from(list));
      selection.current?.empty();
    }
    // 커서가 지우개라면
    if (cursor === 1) {
      const endIdx: number = e.target.dataset["highlightIdx"];
      let list = new Set<number>(highlightList);
      for (let index = startIdx; index <= endIdx; index++) {
        list.delete(index);
      }
      setHighLightList(Array.from(list));
      selection.current?.empty();
    }
  }
  return (
    <>
      {data && (
        <section
          className={cx("news-content", {
            highlight: cursor === 0,
            finder: cursor === 2,
            eraser: cursor === 1,
          })}
        >
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
            {/* <pre className={cx("text")}>{data.newsMainText}</pre> */}
            <pre ref={textEl} className={cx("text", ["red"])}>
              {data.newsPos.map(
                (item: { word: string; pos: string }, index: number) => {
                  const { word, pos } = item;
                  if (pos === "Foreign" && word === "\n\n") {
                    return (
                      <div
                        onMouseUp={(e) => highlightOut(e)}
                        onMouseDown={(e) => highlightEnter(e)}
                        key={index}
                        data-highlight-idx={index}
                      >
                        <br />
                        <br />
                      </div>
                    );
                  } else if (pos === "Noun" || pos === "Alpha") {
                    return (
                      <span
                        onMouseDown={(e) => highlightEnter(e)}
                        onMouseUp={(e) => highlightOut(e)}
                        key={index}
                        className={cx({
                          "dictionary-word": cursor === 2,
                          filled: highlightList.includes(index),
                        })}
                        data-highlight-idx={index}
                      >
                        {word}
                      </span>
                    );
                  } else {
                    return (
                      <span
                        onMouseDown={(e) => highlightEnter(e)}
                        onMouseUp={(e) => highlightOut(e)}
                        key={index}
                        className={cx({
                          filled: highlightList.includes(index),
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
