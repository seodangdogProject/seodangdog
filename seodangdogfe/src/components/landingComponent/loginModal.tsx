import React , { PropsWithChildren } from "react";
import styled from 'styled-components';
import styles from './modal.module.css'

const Backdrop = styled.div`
width: 100vw;
height: 100vh;
position: fixed;
top: 0;
z-index: -10;
background-color: rgba(0, 0, 0, 0.2);
`;

interface ModalDefaultType {
    onClickToggleModal: () => void;
  }
  
  function Modal({
    onClickToggleModal,
    children,
  }: PropsWithChildren<ModalDefaultType>) {
    return (
      <>
      <div className={styles.modal_container}>
        <div className={styles.header_container}>로고</div>
        <div className={styles.form_container}>아이디 비번</div>
        <div className={styles.login_button_container}>
            <button>로그인</button>
        </div>
        <div className={styles.footer}>
            <div>아이디가 없으신가요? 지금 <a className={styles.link}>회원가입</a> 하세요! </div></div>
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
