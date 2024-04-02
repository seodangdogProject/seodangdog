/* eslint-disable react/no-unescaped-entities */
import { Children, useEffect, useRef, useState } from "react";
import styled from "./NewsContent.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "@/utils/http-commons";
import Modal from "./wordModal/wordDetailModal";
import changeDateFormat from "@/utils/changeDateFormat";
import NotSolved from "./NotSolved";
export default function NewsContent({
  keywords,
  data,
  cursor,
  newsSeq,
}: {
  keywords: string[];
  data: any;
  cursor?: number | null;
  newsSeq: number;
}) {
  const cx = classNames.bind(styled);
  const textEl = useRef<HTMLPreElement>(null);
  const selection = useRef(window.getSelection());
  const [prevWordPop, setPrevWordPop] = useState<HTMLElement | null>(null);
  const [startIdx, setStartIdx] = useState<number>(0);
  const [highlightList, setHighLightList] = useState<number[]>([]);
  const [wordList, setWordList] = useState<number[]>([]);
  const [clickedWord, setClickedWord] = useState<any>();
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  // cleanup 함수에서 사용하기 위해 설정한 useRef 변수
  const latestHighlightListRef = useRef<number[]>();
  latestHighlightListRef.current = highlightList;
  const latestWordListRef = useRef<number[]>();
  latestWordListRef.current = wordList;

  useEffect((): any => {
    setHighLightList(data?.highlightList || []);
    setWordList(data?.wordList || []);
    // console.log(data);
  }, [data]);
  useEffect((): any => {
    return async () => {
      const body = {
        newsSeq: newsSeq,
        highlightList: latestHighlightListRef.current,
        wordList: latestWordListRef.current,
      };
      console.log(body);
      try {
        const res = await privateFetch("/news/read", "PATCH", body);
        if (res.status !== 200) {
          throw "통신 에러 발생!";
        }
      } catch (error) {
        console.error(error);
      }
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
  async function getWordMeaning(e: any, text: string, index: number) {
    // 현재 커서가 돋보기인지 유효성검사
    if (cursor !== 2) return;
    try {
      const res = await privateFetch("/news/word/" + text, "GET");
      let data: {
        word: string;
        wordLang: string;
        total: number;
        items: any[];
      } | null = null;
      if (res.status !== 200) {
        throw "서버에러발생";
      } else {
        data = await res.json();
        // setWordInfo(data);
        console.log(data?.items);
        setClickedWord({
          wordSeq: index, // 여기에선 이 기사에서 단어의 인덱스값을냄나타냄
          word: data?.word,
          mean1: data?.items[0].definition,
          mean2: data?.items[1]?.definition,
        });
        setIsModalOpen(true);
        console.log(data);
        console.log(wordList);
      }
    } catch (error) {
      console.error(error);
    }
  }
  // 단어 스크랩 함수
  async function scrapWord() {
    setWordList((prev) => [...prev, clickedWord.wordSeq]);
    const body = {
      word: clickedWord.word,
    };
    await privateFetch("/news/word", "POST", body);
  }
  // 단어 모달의 자식 컴포넌트로 줄 함수
  function Scrap() {
    return (
      <>
        <span className={cx("scrap")}>
          {wordList.includes(clickedWord.wordSeq) ? (
            <img src="/bookmark-full-icon.svg" alt="" />
          ) : (
            <img onClick={scrapWord} src="/bookmark-empty-icon.svg" alt="" />
          )}
        </span>
      </>
    );
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
          {isModalOpen && (
            <div className={cx("word-modal-container")}>
              <Modal
                setWordList={setWordList}
                wordSeq={clickedWord.wordSeq}
                isOpen={isModalOpen}
                clickedWord={clickedWord.word}
                onClose={() => {
                  setIsModalOpen(false);
                }}
              >
                <Scrap />
              </Modal>
            </div>
          )}
          <h1 className={cx("title")}>{data.newsTitle}</h1>
          <div className={cx("date")}>
            {changeDateFormat(data.newsCreatedAt)}
          </div>
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
                        onClick={(e) => getWordMeaning(e, word, index)}
                        key={index}
                        className={cx({
                          "dictionary-word": cursor === 2,
                          filled: highlightList.includes(index),
                          "word-circle": wordList.includes(index),
                        })}
                        data-highlight-idx={index}
                      >
                        {word}
                        {/* {wordList.includes(index) && (
                          <img src="/word-circle-icon.svg" alt="" />
                        )} */}
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
