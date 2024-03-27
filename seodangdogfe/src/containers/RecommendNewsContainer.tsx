"use client";
import { useEffect, useRef, useState } from "react";
import styled from "./RecommendNewsContainer.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "../utils/http-commons";
import { useRouter } from "next/navigation";
import changeDateFormat from "@/utils/changeDateFormat";
export default function RecommendNewsContainer() {
  const cx = classNames.bind(styled);

  const [category, setCategory] = useState<string>("user-recommend");
  const [newsList, setNewsList] = useState<any[]>([]);
  const userRecommendEl = useRef<HTMLDivElement>(null);
  const otherRecommendEl = useRef<HTMLDivElement>(null);

  const router = useRouter();

  // method
  function toggle(inputCategory: string): void {
    if (category === inputCategory) return;
    setCategory((prev) => inputCategory);
    userRecommendEl.current?.classList.toggle(cx("active"));
    otherRecommendEl.current?.classList.toggle(cx("active"));
  }

  useEffect(() => {
    const token = localStorage.getItem("accessToken") || "";
    (async () => {
      try {
        const res = await privateFetch("/main/" + category, "GET");
        const data = (await res.json())[0].newsPreviewList;
        console.log(data);
        let subArrays = [];
        for (let i = 0; data; i += 3) {
          subArrays.push(data.slice(i, i + 3));
        }
        setNewsList(subArrays);
      } catch (error) {
        console.error(error);
      }
    })();
  }, [category]);
  return (
    <>
      <div className={styled.container}>
        <div className={styled.toggle__container}>
          <div className={cx("toggle")}>
            <div
              onClick={() => toggle("user-recommend")}
              ref={userRecommendEl}
              className={cx("toggle__item", "active")}
            >
              내 취향 뉴스
            </div>
            <div
              onClick={() => toggle("other-recommend")}
              ref={otherRecommendEl}
              className={cx("toggle__item")}
            >
              다른 사람 뉴스
            </div>
          </div>
        </div>
        <div className={cx("section", ["box-shodow-custom"])}>
          <ul className={cx("line")}>
            {newsList.map((subGroup, index) => (
              <li key={index} className={cx("item-container")}>
                {subGroup.map((item: any, idx: number) => (
                  <div
                    onClick={() => router.push("news" + item.newsSeq)}
                    key={item.newsSeq}
                    className={cx("line-item")}
                  >
                    <img src={item.newsImgUrl} alt="" />
                    <div className={cx("title")}>{item.newsTitle}</div>
                    <div className={cx("description")}>
                      {item.newsDescription}
                    </div>
                    <div className={cx("date")}>
                      조회수 {item.countView} •{" "}
                      {changeDateFormat(item.newsCreatedAt)}
                    </div>
                  </div>
                ))}
              </li>
            ))}

            <li className={cx("item-container")}>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>
                  조회수 123 • 2024.03.18 오후 06:49분
                </div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
            </li>
            <li className={cx("item-container")}>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
            </li>
            <li className={cx("item-container")}>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
            </li>
            <li className={cx("item-container")}>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
              <div className={cx("line-item")}>
                <img
                  src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                  alt=""
                />
                <div className={cx("title")}>
                  기아 전기차 라인업 한눈에…'EV 트렌드 코리아 2024' 참가
                </div>
                <div className={cx("description")}>
                  기아가 'EV 트렌드 코리아 2024'에 참가해 새로운 모빌리티 시대의
                  경험을 선보인다. △전기차 충전 △공간 및 신기술 △지속가능성
                  등을...
                </div>
                <div className={cx("date")}>조회수 123 • 6개월전</div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </>
  );
}
