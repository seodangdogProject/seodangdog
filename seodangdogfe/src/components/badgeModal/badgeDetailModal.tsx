import React, { useEffect, useState } from "react";
import { useRecoilState, useRecoilValue, RecoilRoot } from "recoil";
import styles from "./modal.module.css";
import CloseModal from "@/assets/closeModal-icon.svg";
import { privateFetch } from "@/utils/http-commons";
import { badgeInfo } from "@/atoms/type";
import LockBadgeIcon from "@/assets/lock-badge-icon.svg";
interface ModalProps {
  onClickToggleModal: () => void;
}

function Modal(props: ModalProps) {
  const [badgeInfo, setBadgeInfo] = useState<badgeInfo[] | undefined>();
  const [status, setStatus] = useState<boolean>(false);
  const [clickedBadge, setClickedBadge] = useState<number | null>();

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
          getBadgeInfo();
        } else {
          console.log("error 발생");
        }
      })();
    }
    setStatus(false);
    setClickedBadge(null);
  };

  return (
    <>
      <div className={styles.modal_container}>
        <div className={styles.header_container}>
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
              <div className={styles.list_container_title}>
                <div className={styles.badgeListTitle}>뱃지 목록</div>
                <div className={styles.horizontal}></div>
              </div>
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
                            {item.userBadgeExp}
                          </span>
                          <span>/</span>
                          <span>{item.badgeDto.badgeCondition}</span>
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
