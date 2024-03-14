import React, { PropsWithChildren, useState } from 'react';
import styled from 'styled-components';
import styles from './modal.module.css';

const Backdrop = styled.div`
    width: 100vw;
    height: 100vh;
    position: fixed;
    top: 0;
    background-color: rgba(0, 0, 0, 0.2);
`;

interface ModalDefaultType {
    onClickToggleModal: () => void;
}

function Modal({
    onClickToggleModal,
    children,
}: PropsWithChildren<ModalDefaultType>) {
    let [inputType, setInputType] = useState('password');
    function passwordToggle() {
        setInputType(inputType === 'text' ? 'password' : 'text');
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
                        X
                    </div>
                    <div>로고 이미지</div>
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
                            <i
                                className={styles.pass_button}
                                onClick={passwordToggle}
                            >
                                i
                            </i>
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
                        <a className={styles.link}>회원가입</a> 하세요!{' '}
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
