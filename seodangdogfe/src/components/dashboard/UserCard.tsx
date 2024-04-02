import React, { useState, useCallback } from "react";
import styled from "./UserCard.module.css";
import classNames from "classnames/bind";
import { badge } from "@/atoms/type";
import UpdateDetailIcon from "@/assets/update-detail-icon.svg";
import BadgeDetailModal from "@/components/badgeModal/badgeDetailModal";
import ModifyNicknameModal from "@/components/modifyNicknameModal/ModifyNicknameModal";

interface userProps {
    nickname: string | undefined;
    userId: string | undefined;
    userBadgeNewsList: badge[] | undefined;
    badgeImgUrl: string | undefined;
}

const UserCard = (props: userProps) => {
    const cx = classNames.bind(styled);
    const [isOpenBadgeModal, setOpenBadgeModal] = useState<boolean>(false);
    const [isOpenmodifyNicknameModal, setOpenmodifyNicknameModal] =
        useState<boolean>(false);

    const onClickToggleBadgeModal = useCallback(() => {
        setOpenBadgeModal(!isOpenBadgeModal);
    }, [isOpenBadgeModal]);

    const onClickToggleModifyModal = useCallback(() => {
        setOpenmodifyNicknameModal(!isOpenmodifyNicknameModal);
    }, [isOpenmodifyNicknameModal]);

    return (
        <div className={cx("container", ["box-shodow-cutom"])}>
            {isOpenBadgeModal && (
                <BadgeDetailModal
                    onClickToggleModal={onClickToggleBadgeModal}
                ></BadgeDetailModal>
            )}
            {isOpenmodifyNicknameModal && (
                <ModifyNicknameModal
                    onClickToggleModal={onClickToggleModifyModal}
                ></ModifyNicknameModal>
            )}
            <div
                className={styled.king}
                style={{
                    cursor: "pointer",
                }}
            >
                <img
                    src={props.badgeImgUrl}
                    alt=""
                    onClick={onClickToggleBadgeModal}
                />
            </div>
            <div className={styled.box}>
                <div className={styled.name}>
                    {props.nickname}{" "}
                    <UpdateDetailIcon
                        style={{ cursor: "pointer" }}
                        onClick={onClickToggleModifyModal}
                    />
                </div>
                <div className={styled.email}>{props.userId}</div>
                <ul className={styled.hashtag}>
                    {props.userBadgeNewsList?.slice(0, 4).map((item, idx) => (
                        <li key={idx} className={styled.item}>
                            # {item.badgeName}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default UserCard;
