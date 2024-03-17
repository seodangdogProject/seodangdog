import styled from "./Cover.module.css";
import classNames from "classnames/bind";
export default function Cover() {
	const cx = classNames.bind(styled);
	return (
		<>
			<div className={styled["quiz-container"]}>
				<h4>위 기사에서 언급된 “수급상황실”은 무엇을 의미합니까?</h4>
				<div className={styled.question}>공급량 조절을 위한 부서</div>
				<div className={styled.question}>긴급한 재고 확인 장소</div>
				<div className={styled.question}>
					물품 수요 및 공급 상활을 모니터링하는 시설
				</div>
				<div className={styled.question}>
					가격 변동에 따른 수요 조절기구
				</div>
			</div>
		</>
	);
}
