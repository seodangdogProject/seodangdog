"use client";
import { publicFetch } from "@/utils/http-commons";
import Link from "next/link";
import React, { PropsWithChildren, useRef, useState } from "react";
import styled from "styled-components";
import CloseModal from "../../assets/closeModal-icon.svg";
import ClosePassword from "../../assets/closePassword-icon.svg";
import LoginLogo from "../../assets/loginLogo-icon.svg";
import OpenPassword from "../../assets/openPassword-icon.svg";
import styles from "./modal.module.css";
import { redirect, useRouter } from "next/navigation";

const Backdrop = styled.div`
    width: 100vw;
    height: 100vh;
    position: fixed;
    display: flex;
    top: 0;
    background-color: rgba(0, 0, 0, 0.4);
    z-index: 50;
`;

interface ModalDefaultType {
    onClickToggleModal: () => void;
}

function Modal({
    onClickToggleModal,
    children,
}: PropsWithChildren<ModalDefaultType>) {
    const router = useRouter();
    let [inputType, setInputType] = useState("password");
    let [closeVisible, setCloseVisibleVisible] = useState(true);
    let [openVisible, setOpenVisibleVisible] = useState(false);

    // 보낼 로그인 데이터 엘리먼트
    const userIdEl = useRef<HTMLInputElement>(null);
    const passwordEl = useRef<HTMLInputElement>(null);

    function passwordToggle() {
        setInputType(inputType === "text" ? "password" : "text");
        setCloseVisibleVisible(closeVisible === true ? false : true);
        setOpenVisibleVisible(openVisible === true ? false : true);
    }

    // 로그인 처리 함수
    async function loginHandler(): Promise<JSON> {
        const body = {
            userId: userIdEl.current?.value,
            password: passwordEl.current?.value,
        };

        const res = await publicFetch("/login", "POST", body);
        if (res.status !== 200) {
            alert("로그인에 실패했습니다.");
            return res;
        }
        const { accessToken, refreshToken } = await res.json();
        window.localStorage.setItem("accessToken", accessToken);
        window.localStorage.setItem("refreshToken", refreshToken);
        router.replace("/main");
        return res;
    }

    function handleKeyDown(event: React.KeyboardEvent<HTMLInputElement>): void {
        if (event.key === "Enter") {
            loginHandler();
        }
    }

    return (
        <>
            <div className={styles.modal_container}>
                <div className={styles.header_container}>
                    <div
                        className={styles.exit_button}
                        onClick={(e: React.MouseEvent) => {
                            if (onClickToggleModal) {
                                onClickToggleModal();
                            }
                        }}
                    >
                        <CloseModal />
                    </div>
                    <div className={styles.logo_container}>
                        <LoginLogo />
                    </div>
                </div>
                <div className={styles.form_container}>
                    <div
                        className={styles.input_group_id}
                        style={{ marginBottom: "50px" }}
                    >
                        <input
                            type="text"
                            id="username"
                            name="inputId"
                            placeholder="아이디"
                            ref={userIdEl}
                        />
                        <div className={styles.horizontal}></div>
                    </div>

                    <div className={styles.input_group_pass}>
                        <div>
                            <input
                                type={inputType}
                                id="username"
                                name="inputPassword"
                                placeholder="비밀번호"
                                ref={passwordEl}
                                onKeyDown={handleKeyDown} // 엔터 키 이벤트 처리
                            />
                            <div
                                className={styles.pass_button}
                                onClick={passwordToggle}
                            >
                                {closeVisible && <ClosePassword />}
                                {openVisible && <OpenPassword />}
                            </div>
                        </div>
                        <div className={styles.horizontal}></div>
                    </div>
                </div>
                <div className={styles.login_button_container}>
                    <button
                        onClick={loginHandler}
                        className={styles.login_button}
                    >
                        LOGIN
                    </button>
                </div>
                {/* <Link className={styles.login_button_container} href="/main">
          <button className={styles.login_button}>LOGIN</button>
        </Link> */}
                <div className={styles.footer}>
                    <div
                        style={{
                            paddingLeft: "20px",
                            paddingRight: "20px",
                        }}
                    >
                        아이디가 없으신가요? 지금{" "}
                        <Link href="/join_game" className={styles.link}>
                            회원가입
                        </Link>{" "}
                        하세요!{" "}
                    </div>
                </div>
            </div>
            <Backdrop
                onClick={(e: React.MouseEvent) => {
                    e.preventDefault();
                    if (onClickToggleModal) {
                        onClickToggleModal();
                    }
                }}
            />
        </>
    );
}

export default Modal;
