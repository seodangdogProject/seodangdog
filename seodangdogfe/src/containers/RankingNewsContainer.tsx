import styled from "./RankingNewsContainer.module.css";
import classNames from "classnames/bind";
export default function RankingNewsContainer() {
  const cx = classNames.bind(styled);
  return (
    <div className={styled.container}>
      <div className={styled.toggle__container}>
        <div className={cx("toggle")}>
          <div className={cx("toggle__item", "active")}>많이 본 뉴스</div>
          <div className={cx("toggle__item")}>많이 푼 뉴스</div>
        </div>
      </div>
      <div className={cx("section", ["box-shodow-custom"])}>
        {/* top3 섹션 START */}
        <div className={cx("top3")}>
          <div className={cx("first")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
          </div>
          <div className={cx("second-third-container")}>
            <div className={cx("second")}>
              <img
                src="https://orgthumb.mt.co.kr/06/2024/03/2024030608204758620_1.jpg"
                alt=""
              />
              <div className={cx("info")}>
                <div className={cx("title")}>
                  [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
                </div>
                <div className={cx("description")}>
                  NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                </div>
                <div className={cx("date")}>2024.03.06. 오전 11:37</div>
              </div>
            </div>
            <div className={cx("third")}>
              <img
                src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
                alt=""
              />
              <div className={cx("info")}>
                <div className={cx("title")}>
                  [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
                </div>
                <div className={cx("description")}>
                  NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                </div>
                <div className={cx("date")}>2024.03.06. 오전 11:37</div>
              </div>
            </div>
          </div>
        </div>
        {/* top3 섹션 END */}
        <ul className={cx("other")}>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
          <li className={cx("other__item")}>
            <img
              src="https://thumb.mt.co.kr/06/2024/03/2024030610501730820_1.jpg/dims/optimize/"
              alt=""
            />
            <div className={cx("info")}>
              <div className={cx("title")}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </div>
              <div className={cx("description")}>
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
                NH농협은행에서 110억원 규모의 업무상 배임이 발생했다.
              </div>
              <div className={cx("date")}>2024.03.06. 오전 11:37</div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  );
}
