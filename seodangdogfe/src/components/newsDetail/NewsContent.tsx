/* eslint-disable react/no-unescaped-entities */
import { useEffect, useRef, useState } from "react";
import styled from "./NewsContent.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "@/utils/http-commons";
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
  const [prevWordPop, setPrevWordPop] = useState<HTMLElement | null>(null);
  const [startIdx, setStartIdx] = useState<number>(0);
  const [highlightList, setHighLightList] = useState<number[]>([]);
  const [wordList, setWordList] = useState<number[]>([]);
  const [wordInfo, setWordInfo] = useState<any>(null);

  useEffect(() => {
    setHighLightList(data?.highlightList || []);
    return () => {
      privateFetch("/news/read", "PATCH", wordList);
    };
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
  // 단어 검색 하기 함수
  async function getDictionary(e: any, text: string, index: number) {
    if (cursor !== 2) return;
    if (prevWordPop !== null) {
      prevWordPop.removeChild(prevWordPop.children[0]);
    }
    const el = document.createElement("div");
    try {
      const res = await privateFetch("/news/word/" + text, "GET");
      let data: {
        word: string;
        wordLang: string;
        total: number;
        items: any[];
      } | null = null;
      if (res.status !== 200) {
        throw "adfasd";
      } else {
        data = await res.json();
        setWordInfo(data);
        console.log(data);
        el.setAttribute("class", cx("dict-modal"));
        // el.innerText = data?.word || "";
        // data?.items.forEach((item) => {
        //   el.innerHTML += item.definition;
        // });
        console.log(wordList);
        setWordList((prev) => [...prev, index]);
        el.innerHTML += data?.items[0].definition;
        setPrevWordPop(e.target);
        e.target.appendChild(el);
      }
    } catch (error) {
      console.error(error);
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
                  // 뛰어 쓰기일 경우
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
                    // 단어일 경우
                  } else if (pos === "Noun" || pos === "Alpha") {
                    return (
                      <span
                        onMouseDown={(e) => highlightEnter(e)}
                        onMouseUp={(e) => highlightOut(e)}
                        onClick={(e) => getDictionary(e, word, index)}
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
