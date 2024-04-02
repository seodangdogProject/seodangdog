import React, { PropsWithChildren, useState, useEffect, Fragment } from "react";
import styled from "styled-components";
import styles from "./modal.module.css";
import CloseModalIcon from "@/assets/closeModal-icon.svg";
import { privateFetch } from "@/utils/http-commons";
import FillBookmarkIcon from "@/assets/bookmark-full-icon.svg";
import EmptyBookmarkIcon from "@/assets/bookmark-empty-icon.svg";
const Backdrop = styled.div`
    width: 100vw;
    height: 100vh;
    position: fixed;
    top: 0;
`;

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    clickedWord: string | null;
    children?: React.ReactNode;
}

type meanitem = {
    subNo: number;
    pos: string;
    definition: string;
    link: string;
};

type word = {
    word: string;
    wordLang: string;
    total: number;
    items: meanitem[];
    exist: boolean;
};

function Modal({
    isOpen,
    onClose,
    clickedWord,
    children,
}: PropsWithChildren<ModalProps>) {
    const [word, setWord] = useState<word>();

    const centerModal = () => {
        const modal = document.querySelector("#modal") as HTMLElement;
        const containerA = document.querySelector("#container") as HTMLElement;

        if (modal && containerA) {
            const containerARect = containerA.getBoundingClientRect();
            const modalWidth = modal.offsetWidth;
            const modalHeight = modal.offsetHeight;
            const leftPos =
                containerARect.left + (containerARect.width - modalWidth) / 2;
            const topPos =
                containerARect.top + (containerARect.height - modalHeight) / 2;

            modal.style.left = leftPos + "px";
            modal.style.top = topPos + "px";
        }
    };

    const saveWord = () => {
        (async () => {
            const res = await privateFetch("/news/word", "POST", {
                word: clickedWord,
            });
            if (res.status === 200) {
                const data = await res.json();
                console.log(data);
                getWord();
            } else {
                console.log("error 발생");
            }
        })();
    };

    const deleteWord = () => {
        (async () => {
            const res = await privateFetch(
                "/news/myword/" + clickedWord,
                "PATCH"
            );
            if (res.status === 200) {
                console.log("ok");
                getWord();
            } else {
                console.log("error 발생");
            }
        })();
    };

    const getWord = () => {
        (async () => {
            const res = await privateFetch("/news/word/" + clickedWord, "GET");
            if (res.status === 200) {
                const data = await res.json();
                console.log(data);
                setWord(data);
            } else {
                console.log("error 발생");
            }
        })();
    };
    useEffect(() => {
        centerModal();
        getWord();
    }, [clickedWord]);
    return (
        <>
            <div className={styles.modal_container} id="modal">
                <div className={styles.title}>
                    <div className={styles.title_word}>
                        {clickedWord}
                        {word?.exist ? (
                            <FillBookmarkIcon
                                className={styles.bookmark_button}
                                onClick={deleteWord}
                            />
                        ) : (
                            <EmptyBookmarkIcon
                                className={styles.bookmark_button}
                                onClick={saveWord}
                            />
                        )}
                    </div>

                    <CloseModalIcon
                        className={styles.exit_button}
                        onClick={(e: React.MouseEvent) => {
                            if (isOpen) {
                                onClose();
                            }
                        }}
                    />
                </div>
                <div className={styles.mean_container}>
                    {word?.items?.map((mean, idx) => (
                        <Fragment key={idx}>
                            <div className={styles.mean}>
                                <span
                                    className={styles.meanIndex}
                                    style={{
                                        cursor: "pointer",
                                        fontWeight: "bold",
                                    }}
                                    onClick={() => window.open(mean.link)}
                                    title="뜻 보러 가기" // hover 시 표시될 메시지
                                >
                                    <span className={styles.meanIndexNumber}>
                                        {idx + 1}
                                    </span>
                                </span>
                                <span>
                                    (
                                    <span
                                        dangerouslySetInnerHTML={{
                                            __html: mean.pos,
                                        }}
                                    />
                                    )
                                    <span
                                        dangerouslySetInnerHTML={{
                                            __html: mean.definition,
                                        }}
                                    />
                                </span>
                            </div>
                            <div className={styles.horizontal}></div>
                        </Fragment>
                    ))}
                </div>
            </div>
            <Backdrop
                onClick={(e: React.MouseEvent) => {
                    e.preventDefault();
                    if (isOpen) {
                        onClose();
                    }
                }}
            />
        </>
    );
}

export default Modal;
