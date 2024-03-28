import Link from "next/link";
import styled from "./RecentNewsPreview.module.css";
import { newsThumbnail } from "@/atoms/type";
import changeDateFormat from "@/utils/changeDateFormat";
interface newsProps {
    seenNewsthumbnail: newsThumbnail | undefined;
    solvedNewsthumbnail: newsThumbnail | undefined;
}

const RecentNewsPreview = (props: newsProps) => {
    return (
        <>
            <div className={styled.container}>
                <div className={styled.last_seen_container}>
                    <h3 className={styled.caption}>
                        <Link href="/dashboard/last-seen">
                            최근 본 기사&#32;&#32;&gt;
                        </Link>
                    </h3>
                    <div className={styled.preview}>
                        <div className={styled.date}>
                            {changeDateFormat(
                                props.seenNewsthumbnail?.newsCreatedAt || ""
                            )}
                        </div>
                        <img src={props.seenNewsthumbnail?.newsImgUrl} alt="" />
                        <div className={styled.body}>
                            <h4 className={styled.title}>
                                {props.seenNewsthumbnail?.newsTitle}
                                {/* [단독]NH농협은행 110억원 배임사고 발생…해당 직원
                                형사고발 */}
                            </h4>
                            <ul className={styled.hashtag}>
                                {props.seenNewsthumbnail?.newsKeyword
                                    .slice(0, 3)
                                    .map((item, index) => (
                                        <li key={index} className={styled.item}>
                                            #{item}
                                        </li>
                                    ))}
                            </ul>
                        </div>
                    </div>
                </div>
                {/* 두 번째 */}
                <div className={styled.last_solved_container}>
                    <h3 className={styled.caption}>
                        최근 푼 기사&#32;&#32;&gt;
                    </h3>
                    <div className={styled.preview}>
                        <div className={styled.date}>
                            {changeDateFormat(
                                props.solvedNewsthumbnail?.newsCreatedAt || ""
                            )}
                        </div>
                        <img
                            src={props.solvedNewsthumbnail?.newsImgUrl}
                            alt=""
                        />
                        <div className={styled.body}>
                            <h4 className={styled.title}>
                                {props.solvedNewsthumbnail?.newsTitle}
                            </h4>
                            <ul className={styled.hashtag}>
                                {props.solvedNewsthumbnail?.newsKeyword
                                    .slice(0, 3)
                                    .map((item, index) => (
                                        <li key={index} className={styled.item}>
                                            #{item}
                                        </li>
                                    ))}
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default RecentNewsPreview;
