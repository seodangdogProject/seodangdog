import React, { PropsWithChildren, useRef, useState } from "react";
import styled from "styled-components";
// css
import styles from "./modal.module.css";

// assets
import OpenPassword from "../../assets/openPassword-icon.svg";
import ClosePassword from "../../assets/closePassword-icon.svg";
import CloseModal from "../../assets/closeModal-icon.svg";
import JoinLogo from "../../assets/joinLogo-icon.svg";
import { publicFetch } from "@/utils/http-commons";
import { tree } from "next/dist/build/templates/app-page";

const Backdrop = styled.div`
    width: 100vw;
    height: 100vh;
    position: fixed;
    top: 0;
    background-color: rgba(0, 0, 0, 0.4);
`;

interface ModalDefaultType {
    onClickToggleModal: () => void;
}

interface ModalProps extends ModalDefaultType {
    data: { id: number; keyword: string }[];
}

function Modal({
    data,
    onClickToggleModal,
    children,
}: PropsWithChildren<ModalProps>) {
    let [inputType, setInputType] = useState("password");
    let [closeVisible, setCloseVisibleVisible] = useState(true);
    let [openVisible, setOpenVisibleVisible] = useState(false);
    let [checkId, setCheckId] = useState(false);

    // 서버로 보낼 데이터들
    const idEl = useRef<HTMLInputElement>(null);
    const passwordEl = useRef<HTMLInputElement>(null);
    const passwordCheckEl = useRef<HTMLInputElement>(null);
    const nicknameEl = useRef<HTMLInputElement>(null);
    const [registrationComplete, setRegistrationComplete] = useState(false);

    // Method

    // 패스워드 *로 숨기기 토글 함수
    function passwordToggle() {
        setInputType(inputType === "text" ? "password" : "text");
        setCloseVisibleVisible(closeVisible === true ? false : true);
        setOpenVisibleVisible(openVisible === true ? false : true);
    }

    const idCheck = async () => {
        const userId = idEl.current ? idEl.current.value : "";
        console.log(userId, " 아이디 중복 체크");

        try {
            const response = await publicFetch(
                `/check-id?userId=${userId}`,
                "POST"
            );

            if (response.ok) {
                if (response.msg == "POSSIBLE") {
                    alert("사용가능한 아이디 입니다.");
                    setCheckId(true);
                } else {
                    alert("이미 존재하는 아이디 입니다.");
                    setCheckId(false);
                }
            } else {
                console.error("아이디 체크 실패");
            }
        } catch (error) {
            console.error("회원가입 중 에러 발생:", error);
        }
    };

    // 회원가입버튼 클릭시 처리하는 로직
    const registHandler = async () => {
        const userId = idEl.current ? idEl.current.value : "";
        const password = passwordEl.current ? passwordEl.current.value : "";
        const passwordCheck = passwordCheckEl.current
            ? passwordCheckEl.current.value
            : "";
        const nickname = nicknameEl.current ? nicknameEl.current.value : "";
        const keywords = data.map((item) => item.keyword);

        if (!userId) {
            alert("아이디는 필수 입력 사항입니다.");
            return;
        }

        if (!password) {
            alert("비밀번호는 필수 입력 사항입니다.");
            return;
        }

        if (!nickname) {
            alert("닉네임은 필수 입력 사항입니다.");
            return;
        }

        if (!checkId) {
            alert("아이디를 중복체크 해주세요");
            return;
        }

        if (password != passwordCheck) {
            alert("비밀번호를 확인해주세요");
            return;
        }

        try {
            const response = await publicFetch("/join", "POST", {
                userId,
                password,
                nickname,
                keywords,
            });

            if (response.ok) {
                console.log("회원가입 성공");
                alert(`${nickname}님, 환영해요!`);
                onClickToggleModal();
                window.location.href = "/";
            } else {
                console.error("회원가입 실패");
            }
        } catch (error) {
            console.error("회원가입 중 에러 발생:", error);
        }
    };

    const handleKeyPress = (event: React.KeyboardEvent) => {
        if (event.key === "Enter") {
            registHandler();
        }
    };

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
                        <JoinLogo />
                    </div>
                </div>
                <div className={styles.form_container}>
                    <div className={styles.input_group_id}>
                        <div
                            style={{
                                display: "flex",
                            }}
                        >
                            <input
                                type="text"
                                id="username"
                                name="inputId"
                                placeholder="아이디"
                                ref={idEl}
                                onKeyPress={handleKeyPress}
                                style={{
                                    width: "70%",
                                }}
                            />
                            <div
                                className={styles.id_check_btn}
                                onClick={idCheck}
                            >
                                중복 체크
                            </div>
                        </div>
                        <div className={styles.horizontal}></div>
                    </div>
                    <div className={styles.input_group_id}>
                        <input
                            type="text"
                            id="nickname"
                            name="inputNickname"
                            placeholder="닉네임"
                            ref={nicknameEl}
                            onKeyPress={handleKeyPress}
                        />
                        <div className={styles.horizontal}></div>
                    </div>

                    <div className={styles.input_group_pass}>
                        <div>
                            <input
                                type={inputType}
                                id="password"
                                name="inputPassword"
                                placeholder="비밀번호"
                                ref={passwordEl}
                                onKeyPress={handleKeyPress}
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

                    <div className={styles.input_group_pass}>
                        <div>
                            <input
                                type={inputType}
                                id="passwordcheck"
                                name="inputPassword"
                                placeholder="비밀번호 확인"
                                ref={passwordCheckEl}
                                onKeyPress={handleKeyPress}
                            />
                        </div>
                        <div className={styles.horizontal}></div>
                    </div>
                </div>
                <div className={styles.login_button_container}>
                    <button
                        onClick={registHandler}
                        className={styles.login_button}
                    >
                        회원가입
                    </button>
                </div>
                <div className={styles.footer}></div>
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
