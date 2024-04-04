"use client";
// WordGame.tsx
import React, { useState, useEffect } from "react";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { loadingState } from "@/atoms/loadingRecoil";
import styles from "./wordlist_layout.module.css";
import {
  koWordListState,
  engWordListState,
  Item,
} from "../../../atoms/wordListRecoil";
import SearchIcon from "../../../assets/search-icon.svg";
import WordDetailModal from "../../../components/wordComponent/wordDetailModal";
import { privateFetch } from "@/utils/http-commons";
import TrashCanIcon from "@/assets/delete-trashcan-icon.svg";
import ToastPopup from "@/components/toast/Toast";
import Loading from "@/app/loading1";

const OneWord: React.FC = () => {
  const engWordList = useRecoilValue(engWordListState);
  const koWordList = useRecoilValue(koWordListState);
  const [language, setLanguage] = useState("ko");
  const [engColor, setEngColor] = useState(styles.unselected_toggle_item);
  const [koColor, setKoColor] = useState(styles.selected_toggle_item);
  const [wordList, setWordList] = useState<Item[]>(koWordList);
  const [isOpenModal, setOpenModal] = useState<boolean>(false);
  const [clickedWord, setClickedWord] = useState<string | null>(null);
  const [searchText, setSearchText] = useState("");
  const setKoWordList = useSetRecoilState(koWordListState);
  const setEngWordList = useSetRecoilState(engWordListState);
  const [toast, setToast] = useState(false);
  // recoil 변수
  const [isLoading] = useRecoilState(loadingState);
  const setLoadingState = useSetRecoilState(loadingState);

  useEffect(() => {
    // 데이터 받아오는 함수 START
    initReq();
  }, []);

  useEffect(() => {
    if (language === "ko") {
      setWordList(koWordList);
    } else {
      setWordList(engWordList);
    }
  }, [language, koWordList, engWordList]);

  const initReq = () => {
    (async () => {
      const res = await privateFetch("/myword", "GET");
      if (res.status === 200) {
        const data = await res.json();
        console.log(data);
        setKoWordList(data.wordList);
        // 영어, 한글 따로 넣기
      } else {
      }
    })();

    (async () => {
      const res = await privateFetch("/myword/EngWord", "GET");
      if (res.status === 200) {
        const data = await res.json();
        console.log(data);
        setEngWordList(data.wordList);
        setLoadingState(false);
      } else {
        console.log("error 발생");
      }
    })();
  };

  // 이동
  const handleOpenModal = (word: Item) => {
    setClickedWord((prevClickedWord) => word.word); // 함수형 업데이트 사용
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  const handleToggleKo = () => {
    console.log("한글로 변경 버튼");
    setLanguage("ko");
    setSearchText(" ");
    setEngColor(styles.unselected_toggle_item);
    setKoColor(styles.selected_toggle_item);
  };

  const handleToggleEng = () => {
    console.log("영어로 변경 버튼");
    setLanguage("eng");
    setSearchText(" ");
    setEngColor(styles.selected_toggle_item);
    setKoColor(styles.unselected_toggle_item);
  };

  const deleteWord = (wordSeq: number) => {
    (async () => {
      const res = await privateFetch("/myword/" + wordSeq, "PATCH");
      if (res.status === 200) {
        console.log("ok");
        initReq(); // 다시 요청하기
      } else {
        console.log("error 발생");
      }
    })();
    setToast(true);
  };

  const handleSearchInputChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSearchText(event.target.value);
  };

  const searchKeyword = () => {
    (async () => {
      console.log(searchText);
      const res = await privateFetch(
        `/myword/search/prefix?prefix=${searchText}`,
        "GET"
      );
      if (res.status === 200) {
        const data = await res.json();
        console.log(data);
        setWordList(data.wordList);
      } else {
        console.log("error 발생");
      }
    })();
  };

  function handleKeyDown(event: React.KeyboardEvent<HTMLInputElement>): void {
    if (event.key === "Enter") {
      searchKeyword();
    }
  }

  return (
    <>
      <div className={styles.container}>
        {isLoading ? (
          <Loading />
        ) : (
          <>
            {isOpenModal && (
              <WordDetailModal
                isOpen={isOpenModal}
                onClose={handleCloseModal}
                clickedWord={clickedWord}
              ></WordDetailModal>
            )}

            <div style={{ marginLeft: "50px" }}>
              {toast && (
                <ToastPopup
                  setToast={setToast}
                  message={"삭제가 완료되었습니다."}
                />
              )}
            </div>

            <div className={styles.content_cotainer}>
              <div className={styles.search_cotinaer}>
                <input
                  type="text"
                  value={searchText}
                  className={styles.search_input}
                  onChange={handleSearchInputChange}
                  onKeyDown={handleKeyDown}
                />
                <SearchIcon
                  onClick={(e: React.MouseEvent<SVGSVGElement, MouseEvent>) => {
                    e.stopPropagation();
                    searchKeyword();
                  }}
                />
              </div>
              <div className={styles.toggel_container}>
                <div
                  className={`${styles.toggle_item} ${koColor}`}
                  onClick={handleToggleKo}
                >
                  한글
                </div>
                <div
                  className={`${styles.toggle_item} ${engColor}`}
                  onClick={handleToggleEng}
                >
                  영어
                </div>
              </div>
              <div className={styles.background_container}>
                {wordList.length > 0 ? (
                  wordList.map((item, index) => (
                    <div
                      key={item.wordSeq}
                      className={styles.wordBox}
                      onClick={() => handleOpenModal(item)}
                    >
                      <div className={styles.titleContainer}>
                        <div className={styles.word}>{item.word}</div>
                        <TrashCanIcon
                          style={{
                            marginRight: "5%",
                            cursor: "pointer",
                            zIndex: "999px",
                          }}
                          onClick={(
                            e: React.MouseEvent<SVGSVGElement, MouseEvent>
                          ) => {
                            e.stopPropagation();
                            deleteWord(item.wordSeq);
                          }}
                        />
                      </div>

                      <div className={styles.mean_box}>
                        <div className={styles.mean}>
                          1.
                          <span
                            dangerouslySetInnerHTML={{
                              __html: item.mean1,
                            }}
                          />
                        </div>
                        {item.mean2 != null && (
                          <div className={styles.mean}>
                            2.
                            <span
                              dangerouslySetInnerHTML={{
                                __html: item.mean2,
                              }}
                            />
                          </div>
                        )}
                      </div>
                    </div>
                  ))
                ) : (
                  <div className={styles.no_word_message}>
                    저장한 단어가 없어요.
                  </div>
                )}
              </div>
            </div>
          </>
        )}
      </div>
    </>
  );
};

export default OneWord;
