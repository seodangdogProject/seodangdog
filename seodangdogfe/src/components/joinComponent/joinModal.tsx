import React, { PropsWithChildren, useRef, useState } from "react";
import { useRecoilState, useRecoilValue, RecoilRoot } from "recoil";
import Link from "next/link";
import styled from "styled-components";
// css
import styles from "./modal.module.css";

//recoil
import { userKeywords } from "../../atoms/joinRecoil";

// assets
import OpenPassword from "../../assets/openPassword-icon.svg";
import ClosePassword from "../../assets/closePassword-icon.svg";
import CloseModal from "../../assets/closeModal-icon.svg";
import JoinLogo from "../../assets/joinLogo-icon.svg";
import { publicFetch } from "@/utils/http-commons";

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

  // 서버로 보낼 데이터들
  const idEl = useRef<HTMLInputElement>(null);
  const passwordEl = useRef<HTMLInputElement>(null);
  const nicknameEl = useRef<HTMLInputElement>(null);

  // Method

  // 패스워드 *로 숨기기 토글 함수
  function passwordToggle() {
    setInputType(inputType === "text" ? "password" : "text");
    setCloseVisibleVisible(closeVisible === true ? false : true);
    setOpenVisibleVisible(openVisible === true ? false : true);
  }

  // 회원가입버튼 클릭시 처리하는 로직
  function registHandler() {
    const keywordIdList = data.map((item) => item.id);
    publicFetch("/join", "GET");
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
            <JoinLogo />
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
              ref={idEl}
            />
            <div className={styles.horizontal}></div>
          </div>
          <div
            className={styles.input_group_id}
            style={{ marginBottom: "50px" }}
          >
            <input
              type="text"
              id="nickname"
              name="inputNickname"
              placeholder="닉네임"
              ref={nicknameEl}
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
              />
              <div className={styles.pass_button} onClick={passwordToggle}>
                {closeVisible && <ClosePassword />}
                {openVisible && <OpenPassword />}
              </div>
            </div>
            <div className={styles.horizontal}></div>
          </div>
        </div>
        {/* <div className={styles.login_button_container}>
          <button onClick={registHandler} className={styles.login_button}>
            Sign up
          </button>
        </div> */}
        <Link href="/landing" className={styles.login_button_container}>
          <button className={styles.login_button}>Sign up</button>
        </Link>
        <div className={styles.footer}>
          select : {data.length}
          {/* {data
            .map((keyword) => `${keyword.keyword}(${keyword.id})`)
            .join(", ")} */}
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
