"use client";
import React, { useEffect } from "react";
import styled from "./DashboardContainer.module.css";
import classNames from "classnames/bind";
import RecentNewsPreview from "@/components/dashboard/RecentNewsPreview";
import UserCard from "@/components/dashboard/UserCard";
import Strict from "@/components/strict/strict";
import Chart from "@/components/chart/chart";
import { privateFetch } from "@/utils/http-commons";
import { mypageState } from "@/atoms/userRecoil";
import {
    useRecoilState,
    RecoilRoot,
    useRecoilCallback,
    useRecoilValue,
    useSetRecoilState,
} from "recoil";
import { MyPageDto } from "@/atoms/type";

function Info() {
    return <></>;
}

function State() {
    return <></>;
}

export default function DashboardContainer() {
    const mypageDto = useRecoilValue<MyPageDto | null>(mypageState);

    const setMyPageDto = useSetRecoilState(mypageState);

    useEffect(() => {
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
    }, []);

    const cx = classNames.bind(styled);
    return (
        <>
            <div className={cx("container")}>
                <div className={cx("dashboard")}>
                    <div className={cx("dashboard__top")}>
                        <div
                            className={cx(
                                "dashboard__top__first",
                                "padding-item",
                                ["bg-light-purple"]
                            )}
                        >
                            <div className={styled.title}>Info</div>
                            <div className={cx("user", ["box-shodow-custom"])}>
                                <UserCard
                                    nickname={mypageDto?.nickname}
                                    userId={mypageDto?.userId}
                                    userBadgeNewsList={mypageDto?.userBadgeList}
                                    badgeImgUrl={mypageDto?.badgeImgUrl}
                                />
                            </div>
                            <div
                                className={cx("streak", ["box-shodow-custom"])}
                            >
                                <Strict dates={mypageDto?.streakList}></Strict>
                            </div>
                        </div>
                        <div
                            className={cx(
                                "dashboard__top__second",
                                "padding-item",
                                ["bg-light-purple"]
                            )}
                        >
                            <div className={styled.title}>워드클라우드</div>
                            <div
                                className={cx("wordcloud", [
                                    "box-shodow-custom",
                                ])}
                            >
                                <div
                                    className={cx("boxshadow", [
                                        "box-shodow-custom",
                                    ])}
                                >
                                    <img
                                        src={mypageDto?.wordCloudImgUrl}
                                        alt="워드 클라우드 이미지"
                                        style={{
                                            overflow: "hidden",
                                        }}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                    {/* 두 번째 */}
                    <div
                        className={cx("dashboard__bottom", "bg__light__purple")}
                    >
                        <div
                            className={cx("dashboard__bottom__first", [
                                "bg-light-purple",
                            ])}
                        >
                            <RecentNewsPreview
                                seenNewsthumbnail={mypageDto?.recentViewNews}
                                solvedNewsthumbnail={
                                    mypageDto?.recentSolvedNews
                                }
                            />
                        </div>
                        <div
                            className={cx(
                                "dashboard__bottom__second",
                                ["bg-light-purple"],
                                "padding-item"
                            )}
                        >
                            <div className={styled.title}>능력치</div>
                            <div className={cx("state", ["box-shodow-custom"])}>
                                <Chart ability={mypageDto?.ability} />
                            </div>
                            <State />
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
