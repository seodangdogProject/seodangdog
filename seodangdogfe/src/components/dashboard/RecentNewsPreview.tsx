import Link from "next/link";
import styled from "./RecentNewsPreview.module.css";
import { newsThumbnail } from "@/atoms/type";
import changeDateFormat from "@/utils/changeDateFormat";
import createQueryString from "@/utils/createQueryString";
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
            <Link href="/dashboard/last-seen">최근 본 기사&#32;&#32;&gt;</Link>
          </h3>
          <div className={styled.preview}>
            {props.seenNewsthumbnail ? (
              <>
                <Link
                  style={{
                    color: "#000",
                    display: "flex",
                    width: "100%",
                  }}
                  href={`/news/${
                    props.seenNewsthumbnail.newsSeq
                  }?${createQueryString("user", true)}`}
                >
                  <div className={styled.date}>
                    {changeDateFormat(
                      props.seenNewsthumbnail?.newsCreatedAt || ""
                    )}
                  </div>
                  <img
                    src={
                      props.seenNewsthumbnail?.newsImgUrl == "None"
                        ? "/images/default-news-image.jpg"
                        : props.seenNewsthumbnail?.newsImgUrl
                    }
                    alt=""
                  />
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
                </Link>
              </>
            ) : (
              <>
                <div className={styled.empty_preview}>
                  최근 본 뉴스가 존재하지 않습니다.
                </div>
              </>
            )}
          </div>
        </div>
        {/* 두 번째 */}
        <div className={styled.last_solved_container}>
          <h3 className={styled.caption}>
            <Link href="/dashboard/last-solved">
              최근 푼 기사&#32;&#32;&gt;
            </Link>
          </h3>
          <div className={styled.preview}>
            {props.solvedNewsthumbnail ? (
              <>
                <Link
                  style={{
                    color: "#000",
                    display: "flex",
                    width: "100%",
                  }}
                  href={`/news/${
                    props.solvedNewsthumbnail.newsSeq
                  }?${createQueryString("user", true)}`}
                >
                  <div className={styled.date}>
                    {changeDateFormat(
                      props.solvedNewsthumbnail?.newsCreatedAt || ""
                    )}
                  </div>
                  <img
                    src={
                      props.solvedNewsthumbnail?.newsImgUrl == "None"
                        ? "/images/default-news-image.jpg"
                        : props.solvedNewsthumbnail?.newsImgUrl
                    }
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
                </Link>
              </>
            ) : (
              <>
                <div className={styled.empty_preview}>
                  최근 푼 뉴스가 존재하지 않습니다.{" "}
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default RecentNewsPreview;
