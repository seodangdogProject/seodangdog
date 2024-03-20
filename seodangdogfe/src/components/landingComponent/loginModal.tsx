import React, { PropsWithChildren, useState } from 'react';
import Link from 'next/link';
import styled from 'styled-components';
import styles from './modal.module.css';
import OpenPassword from '../../assets/openPassword-icon.svg';
import ClosePassword from '../../assets/closePassword-icon.svg';
import CloseModal from '../../assets/closeModal-icon.svg';
import LoginLogo from '../../assets/loginLogo-icon.svg';

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
    let [inputType, setInputType] = useState('password');
    let [closeVisible, setCloseVisibleVisible] = useState(true);
    let [openVisible, setOpenVisibleVisible] = useState(false);

    function passwordToggle() {
        setInputType(inputType === 'text' ? 'password' : 'text');
        setCloseVisibleVisible(closeVisible === true ? false : true);
        setOpenVisibleVisible(openVisible === true ? false : true);
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
                        style={{ marginBottom: '50px' }}
                    >
                        <input
                            type="text"
                            id="username"
                            name="inputId"
                            placeholder="아이디"
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
                    <button className={styles.login_button}>LOGIN</button>
                </div>
                <div className={styles.footer}>
                    <div
                        style={{
                            paddingLeft: '20px',
                            paddingRight: '20px',
                        }}
                    >
                        아이디가 없으신가요? 지금{' '}
                        <Link href="/join_game" className={styles.link}>
                            회원가입
                        </Link>{' '}
                        하세요!{' '}
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
