"use client";
import React, { useEffect, useState } from "react";
import Link from "next/link";
import styled from "./LastNewsList.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "@/utils/http-commons";
import { newsDetailThumbnail } from "@/atoms/type";
import changeDateFormat from "@/utils/changeDateFormat";

function CardNews({ data }: { data: newsDetailThumbnail }) {
    return (
        <div className={styled.cardNews}>
            <div className={styled.imageContainer}>
            <img
                src={
                    data.newsImgUrl == "None"
                        ? "/images/default-news-image.jpg"
                        : data.newsImgUrl
                }
                alt=""
            />
            </div>
            <div className={styled.desc}>
                <div className={styled.title}>{data.newsTitle}</div>
                <div className={styled.meta}>
                    조회수 {data.countView} | {changeDateFormat(
                                                data.newsCreatedAt
                                            )}
                </div>
                <div className={styled.company}>
                    <img src={data.media} alt="" />
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
    const [newsList, setNewsList] = useState<newsDetailThumbnail[]>([]);
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
                                key={idx}
                                style={{ color: "#000" }}
                                href={`/news/${item.newsSeq}`}
                            >
                                <CardNews key={idx} data={item} />
                            </Link>
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
}
