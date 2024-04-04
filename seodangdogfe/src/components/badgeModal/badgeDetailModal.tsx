import React, { useEffect, useState } from "react";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import styles from "./modal.module.css";
import CloseModal from "@/assets/closeModal-icon.svg";
import { privateFetch } from "@/utils/http-commons";
import { badgeInfo } from "@/atoms/type";
import LockBadgeIcon from "@/assets/lock-badge-icon.svg";
import { mypageState } from "@/atoms/userRecoil";
import { MyPageDto } from "@/atoms/type";
import ToastPopup from "@/components/toast/Toast";

interface ModalProps {
  onClickToggleModal: () => void;
}

function Modal(props: ModalProps) {
  const [toast, setToast] = useState(false);
  const [badgeInfo, setBadgeInfo] = useState<badgeInfo[] | undefined>();
  const [status, setStatus] = useState<boolean>(false);
  const [clickedBadge, setClickedBadge] = useState<number | null>();
  const mypageDto = useRecoilValue<MyPageDto | null>(mypageState);
  const setMyPageDto = useSetRecoilState(mypageState);

  useEffect(() => {
    // 데이터 받아오는 함수 START
    getBadgeInfo();
  }, []);

  const getBadgeInfo = () => {
    (async () => {
      const res = await privateFetch("/badges/info", "GET");
      if (res.status === 200) {
        const data = await res.json();
        console.log(data);
        setBadgeInfo(data);
      } else {
        console.log("error 발생");
      }
    })();
  };

  const handleClick = (badgeInfoItem: badgeInfo) => {
    if (badgeInfoItem.collected) {
      setClickedBadge(badgeInfoItem.badgeDto.badgeSeq);
    }
  };

  const saveHandling = () => {
    if (clickedBadge != null) {
      (async () => {
        const res = await privateFetch(
          `/badges/rep-badge?badgeSeq=${clickedBadge}`,
          "PATCH"
        );
        if (res.status === 200) {
          const data = await res.json();
          console.log(data);
          setToast(true);
          getBadgeInfo();
          updateUserInfo();
        } else {
          console.log("error 발생");
        }
      })();
    }
    setStatus(false);
    setClickedBadge(null);
  };

  const updateUserInfo = () => {
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

  return (
    <>
      <div className={styles.modal_container}>
        {toast && (
          <ToastPopup setToast={setToast} message={"변경이 완료되었습니다."} />
        )}

        <div className={styles.header_container}>
          <div className={styles.badgeListTitle}>뱃지 목록</div>
          <div className={styles.horizontal}></div>
          <div
            className={styles.exit_button}
            onClick={(e: React.MouseEvent) => {
              if (props.onClickToggleModal) {
                props.onClickToggleModal();
              }
            }}
          >
            <CloseModal />
          </div>
        </div>
        <div className={styles.modal_content}>
          <div className={styles.right}>
            <div className={styles.list_container}>
              <div className={styles.list_container_title}></div>
              <div className={styles.badgeGrid}>
                {badgeInfo?.map((item, idx) => (
                  <div
                    key={idx}
                    className={`${styles.item} ${
                      item.represent === true ? styles.rep : ""
                    } ${
                      clickedBadge == item.badgeDto.badgeSeq
                        ? styles.selected
                        : " "
                    }`}
                    style={{
                      cursor: status ? "pointer" : "auto",
                    }}
                    onClick={() => handleClick(item)}
                  >
                    <img src={item.badgeDto.badgeImgUrl}></img>
                    <div className={styles.name}>{item.badgeDto.badgeName}</div>
                    {item.collected == false && (
                      <LockBadgeIcon className={styles.lock}></LockBadgeIcon>
                    )}
                    {item.collected == false && (
                      <div className={styles.not_collected}></div>
                    )}
                    {item.collected == false && (
                      <div
                        className={styles.overlay_grey}
                        style={{
                          color: "white",
                        }}
                      >
                        <div className={styles.overlay_name}>
                          {item.badgeDto.badgeName}
                        </div>
                        <div className={styles.overlay_des}>
                          {item.badgeDto.badgeDescription}
                        </div>
                        <div className={styles.overlay_condTitle}>진행도</div>
                        {}
                        <div className={styles.overlay_cond}>
                          <span
                            className={styles.overlay_userExp}
                            style={{
                              color: "yellow",
                            }}
                          >
                            {item.userBadgeExp}
                          </span>
                          <span>/</span>
                          <span>{item.badgeDto.badgeCondition}</span>
                        </div>
                      </div>
                    )}
                    {item.collected && (
                      <div className={styles.overlay}>
                        <div className={styles.overlay_name}>
                          {item.badgeDto.badgeName}
                        </div>
                        <div className={styles.overlay_des}>
                          {item.badgeDto.badgeDescription}
                        </div>
                        <div className={styles.overlay_condTitle}>진행도</div>
                        <div className={styles.overlay_cond}>
                          <span className={styles.overlay_userExp}>
                            수집 완료
                          </span>
                        </div>
                      </div>
                    )}
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
        <div className={styles.button_container}>
          <div className={styles.change} onClick={saveHandling}>
            변경하기
          </div>
        </div>
      </div>
    </>
  );
}

export default Modal;
