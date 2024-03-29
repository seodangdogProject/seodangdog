import React, { useEffect, useState, useRef } from "react";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import styles from "./modal.module.css";
import CloseModal from "@/assets/closeModal-icon.svg";
import { MyPageDto } from "@/atoms/type";
import { mypageState } from "@/atoms/userRecoil";
import { privateFetch } from "@/utils/http-commons";
import { keywordWeight } from "@/atoms/type";
import ToastPopup from "@/components/toast/Toast";

interface ModalProps {
    onClickToggleModal: () => void;
}

function Modal(props: ModalProps) {
    const mypageDto = useRecoilValue<MyPageDto | null>(mypageState);
    const [toast, setToast] = useState(false);
    const [refNickname, setRefNickname] = useState<string[]>([]);
    const setMyPageDto = useSetRecoilState(mypageState);
    const [nickname, setNickname] = useState<string>(
        mypageDto?.nickname ? mypageDto.nickname : ""
    );
    const randomKeyword: keywordWeight[] = [];
    let randomNicknameSet: string[] = [];

    useEffect(() => {
        setRefNickname([]);
        randomNicknameSet = [];
        if (mypageDto?.wordCloudKeywords != undefined) {
            while (
                randomKeyword.length < 4 &&
                mypageDto?.wordCloudKeywords.length > 0
            ) {
                const randomIndex = Math.floor(
                    Math.random() * mypageDto?.wordCloudKeywords.length
                );
                const selectedWord = mypageDto?.wordCloudKeywords[randomIndex];
                randomKeyword.push(selectedWord);
            }

            for (let i = 0; i < randomKeyword.length; i++) {
                const word1 = randomKeyword[i]?.text ?? "";
                const combinedString = `${word1} 매니아`;
                randomNicknameSet.push(combinedString);
            }

            setRefNickname(randomNicknameSet);
        }
    }, []);

    const updateInfo = () => {
        const body = {
            nickname: nickname,
        };

        (async () => {
            const res = await privateFetch("/mypages/nickname", "PATCH", body);
            if (res.status === 200) {
                const data = await res.json();
                reloadInfo();
                setToast(true);
            } else {
                console.log("error 발생");
            }
        })();
    };

    const reloadInfo = () => {
        // 데이터 받아오는 함수 START
        (async () => {
            const res = await privateFetch("/mypages", "GET");
            if (res.status === 200) {
                const data = await res.json();
                console.log(data);
                console.log(data.streakList);
                setMyPageDto(data);
            } else {
                console.log("error 발생");
            }
        })();
    };

    // 입력 값이 변경될 때 실행되는 함수
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setNickname(event.target.value); // 새로운 값을 상태에 설정
    };

    const refChange = (item: string) => {
        setNickname(item);
    };

    return (
        <>
            <div className={styles.modal_container}>
                {toast && (
                    <ToastPopup
                        setToast={setToast}
                        message={"변경이 완료되었습니다."}
                    />
                )}
                <div className={styles.header_container}>
                    <div className={styles.title}>
                        <div className={styles.title_content}>
                            닉네임 변경하기
                        </div>
                        <div className={styles.horizontal}></div>
                    </div>
                    <CloseModal
                        className={styles.exit_button}
                        onClick={(e: React.MouseEvent) => {
                            if (props.onClickToggleModal) {
                                props.onClickToggleModal();
                            }
                        }}
                    />
                </div>

                <div className={styles.content_container}>
                    <div className={styles.recommend_container}>
                        <div className={styles.recommend_title}>
                            {" "}
                            이건 어때요 ?{" "}
                        </div>
                        <div className={styles.recomend_items}>
                            {refNickname.map((item, idx) => (
                                <div
                                    key={idx}
                                    className={styles.recommend_item}
                                    onClick={() => refChange(item)}
                                    style={{
                                        cursor: "pointer",
                                    }}
                                >
                                    {item}
                                </div>
                            ))}
                        </div>
                    </div>
                    <div className={styles.input_container}>
                        <input
                            className={styles.input_box}
                            value={nickname} // 상태에 있는 값으로 입력 필드의 값 설정
                            onChange={handleChange}
                            style={{
                                fontFamily: "hunminjungum",
                            }}
                        ></input>
                        <div className={styles.saveButton} onClick={updateInfo}>
                            저장하기
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Modal;
