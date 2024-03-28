import React, { useState, useCallback } from "react";
import styled from "./UserCard.module.css";
import classNames from "classnames/bind";
import { badge } from "@/atoms/type";
import BadgeDetailIcon from "@/assets/badge-detail-icon.svg";
import BadgeDetailModal from "@/components/badgeModal/badgeDetailModal";
interface userProps {
    nickname: string | undefined;
    userId: string | undefined;
    userBadgeNewsList: badge[] | undefined;
    badgeImgUrl: string | undefined;
}

const UserCard = (props: userProps) => {
    const cx = classNames.bind(styled);
    const [isOpenModal, setOpenModal] = useState<boolean>(false);

    const onClickToggleModal = useCallback(() => {
        setOpenModal(!isOpenModal);
    }, [isOpenModal]);

    return (
        <div className={cx("container", ["box-shodow-cutom"])}>
            {isOpenModal && (
                <BadgeDetailModal
                    onClickToggleModal={onClickToggleModal}
                ></BadgeDetailModal>
            )}
            <div className={styled.king}>
                <img src={props.badgeImgUrl} alt="" />
            </div>
            <div className={styled.box}>
                <div className={styled.name}>
                    {props.nickname}{" "}
                    <BadgeDetailIcon onClick={onClickToggleModal} />
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
