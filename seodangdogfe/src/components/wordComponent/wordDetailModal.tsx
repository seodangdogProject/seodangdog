import React, { PropsWithChildren, useState } from "react";
import Link from "next/link";
import styled from "styled-components";
import styles from "./modal.module.css";
import OpenPassword from "../../assets/openPassword-icon.svg";
import ClosePassword from "../../assets/closePassword-icon.svg";
import CloseModal from "../../assets/closeModal-icon.svg";
import LoginLogo from "../../assets/loginLogo-icon.svg";
import { saved_word } from "@/atoms/type";

const Backdrop = styled.div`
    width: 100vw;
    height: 100vh;
    position: fixed;
    top: 0;
`;

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    clickedWord: saved_word | null;
}

function Modal({
    isOpen,
    onClose,
    clickedWord,
}: PropsWithChildren<ModalProps>) {
    let [inputType, setInputType] = useState("password");
    let [closeVisible, setCloseVisibleVisible] = useState(true);
    let [openVisible, setOpenVisibleVisible] = useState(false);

    function passwordToggle() {
        setInputType(inputType === "text" ? "password" : "text");
        setCloseVisibleVisible(closeVisible === true ? false : true);
        setOpenVisibleVisible(openVisible === true ? false : true);
    }

    return (
        <>
            <div className={styles.modal_container}>
                <div className={styles.title}>{clickedWord?.word}</div>
                {/* <div className={styles.mean_container}>
                    {clickedWord?.mean.map((mean, idx) => (
                        <div key={idx} className={styles.mean}>
                            {mean}
                        </div>
                    ))}
                </div> */}
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
