"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import styled from "./LastNewsList.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "@/utils/http-commons";
import { newsThumbnail } from "@/atoms/type";

function CardNews({ data }: { data: newsThumbnail }) {
    return (
        <div className={styled.cardNews}>
            <img src={data.newsImgUrl} alt="" />
            <div className={styled.desc}>
                <div className={styled.title}>{data.newsTitle}</div>
                <div className={styled.meta}>
                    조회수 {data.countView} | {data.newsCreatedAt}
                </div>
                <div className={styled.company}>
                    <img
                        src="https://mimgnews.pstatic.net/image/upload/office_logo/018/2018/08/08/logo_018_57_20180808174308.png"
                        alt=""
                    />
                </div>
                <div className={styled.summary}>{data.newsDescription}</div>
                <ul className={styled.hashtag}>
                    {data.newsKeyword.slice(0, 3).map((item, idx) => (
                        <li key={idx} className={styled.item}>
                            {item}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}
export default function LastNewsList() {
    const cx = classNames.bind(styled);
    const [newsList, setNewsList] = useState<newsThumbnail[]>([]);
    //키워드 추출
    useEffect(() => {
        const fetchKeywords = async () => {
            try {
                const response = await privateFetch(
                    "/mypages/recent_seen",
                    "GET"
                );
                const data = await response.json();
                setNewsList(data[0].newsPreviewList);
            } catch (error) {
                console.error("error:", error);
            }
        };

        fetchKeywords();
    }, []);

    return (
        <>
            <div className={styled.container}>
                <div className={cx("box,", ["box-shodow-custom"])}>
                    <div className={styled.head}>
                        <h3>최근 본 기사</h3>
                        <img src="/prev-arrow-icon.svg" alt="" />
                    </div>
                    <div className={styled.content}>
                        {newsList.map((item, idx) => (
                            <Link
                                style={{ color: "#000" }}
                                href={`/news/${item.newsSeq}`}
                            >
                                <CardNews data={item} />
                            </Link>
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
}
