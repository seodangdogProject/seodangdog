"use client";
import React, { useEffect } from "react";
import styled from "./DashboardContainer.module.css";
import classNames from "classnames/bind";
import RecentNewsPreview from "@/components/dashboard/RecentNewsPreview";
import UserCard from "@/components/dashboard/UserCard";
import Strict from "@/components/strict/strict";
import Chart from "@/components/chart/chart";
import WordCloud from "@/components/wordCloud/wordCloud";
import { privateFetch } from "@/utils/http-commons";
import { mypageState, nicknameState } from "@/atoms/userRecoil";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { MyPageDto } from "@/atoms/type";

function State() {
    return <></>;
}

export default function DashboardContainer() {
    const mypageDto = useRecoilValue<MyPageDto | null>(mypageState);
    const setMyPageDto = useSetRecoilState(mypageState);
    const usernickname = useRecoilValue<string | null>(nicknameState);
    const setUserNickname = useSetRecoilState(nicknameState);

    useEffect(() => {
        // 데이터 받아오는 함수 START
        (async () => {
            const res = await privateFetch("/mypages", "GET");
            if (res.status === 200) {
                const data = await res.json();
                console.log(data);
                console.log(data.streakList);
                setMyPageDto(data);
                setUserNickname(data.nickname);
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
                            <div className={styled.title}>내 정보</div>
                            <div className={cx("user", ["box-shodow-custom"])}>
                                <UserCard
                                    nickname={usernickname!}
                                    userId={mypageDto?.userId}
                                    userBadgeNewsList={mypageDto?.userBadgeList}
                                    badgeImgUrl={mypageDto?.badgeImgUrl}
                                />
                            </div>
                            <div style={{ padding: "5px" }}></div>
                            <div className={styled.title}>출석부</div>
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
                            <div className={styled.title}>관심 키워드</div>
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
                                    <WordCloud
                                        wordCloudKeywords={
                                            mypageDto?.wordCloudKeywords
                                        }
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
