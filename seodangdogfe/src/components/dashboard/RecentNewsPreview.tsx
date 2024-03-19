import styled from "./RecentNewsPreview.module.css";
export default function RecentNewsPreview() {
  return (
    <>
      <div className={styled.container}>
        <div className={styled.last_seen_container}>
          <h3 className={styled.caption}>최근 본 기사&#32;&#32;&gt;</h3>
          <div className={styled.preview}>
            <div className={styled.date}>2024.03.06. 오전 11:37</div>
            <img
              src="https://imgnews.pstatic.net/image/015/2024/03/17/0004960840_001_20240318003801027.jpg?type=w647"
              alt=""
            />
            <div className={styled.body}>
              <h4 className={styled.title}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </h4>
              <ul className={styled.hashtag}>
                <li className={styled.item}># 은행</li>
                <li className={styled.item}># 배임</li>
              </ul>
            </div>
          </div>
        </div>
        {/* 두 번째 */}
        <div className={styled.last_solved_container}>
          <h3 className={styled.caption}>최근 본 기사&#32;&#32;&gt;</h3>
          <div className={styled.preview}>
            <div className={styled.date}>2024.03.06. 오전 11:37</div>
            <img
              src="https://imgnews.pstatic.net/image/015/2024/03/17/0004960840_001_20240318003801027.jpg?type=w647"
              alt=""
            />
            <div className={styled.body}>
              <h4 className={styled.title}>
                [단독]NH농협은행 110억원 배임사고 발생…해당 직원 형사고발
              </h4>
              <ul className={styled.hashtag}>
                <li className={styled.item}># 은행</li>
                <li className={styled.item}># 배임</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
