"use client";
import { useEffect } from "react";
import styled from "./RecommendNewsContainer.module.css";
import classNames from "classnames/bind";
import { privateFetch } from "../utils/http-commons";
export default function RecommendNewsContainer() {
  const cx = classNames.bind(styled);

  useEffect(() => {
    const token = localStorage.getItem("accessToken") || "";
    (async () => {
      const res = await privateFetch("/main/user-recommend", "GET");
      // console.log(await res.json());
    })();
  }, []);
  return (
    <>
      <div className={styled.container}>
        <div className={styled.toggle__container}>
          <div className={cx("toggle")}>
            <div className={cx("toggle__item", "active")}>내 취향 뉴스</div>
            <div className={cx("toggle__item")}>다른 사람 뉴스</div>
          </div>
        </div>
        <div className={cx("section", ["box-shodow-custom"])}>
          <ul className={cx("line")}>
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
