import React, { PropsWithChildren, useState, useEffect, Fragment } from "react";
import Link from "next/link";
import styled from "styled-components";
import styles from "./modal.module.css";
import OpenPassword from "../../assets/openPassword-icon.svg";
import ClosePassword from "../../assets/closePassword-icon.svg";
import CloseModal from "../../assets/closeModal-icon.svg";
import LoginLogo from "../../assets/loginLogo-icon.svg";
import { saved_word } from "@/atoms/type";
import CloseModalIcon from "@/assets/closeModal-icon.svg";
import { privateFetch } from "@/utils/http-commons";

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
};

function Modal({
    isOpen,
    onClose,
    clickedWord,
    children,
}: PropsWithChildren<ModalProps>) {
    let [inputType, setInputType] = useState("password");
    let [closeVisible, setCloseVisibleVisible] = useState(true);
    let [openVisible, setOpenVisibleVisible] = useState(false);
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

    useEffect(() => {
        centerModal();
        // 데이터 받아오는 함수 START
        (async () => {
            setWord(undefined);
            const res = await privateFetch("/news/word/" + clickedWord, "GET");
            if (res.status === 200) {
                const data = await res.json();
                console.log(data);
                setWord(data);
            } else {
                console.log("error 발생");
            }
        })();
    }, []);
    return (
        <>
            <div className={styles.modal_container} id="modal">
                <div className={styles.title}>
                    <div className={styles.title_word}>{clickedWord}</div>
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
