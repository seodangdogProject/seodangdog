import NewsDetail from "@/containers/NewsDetail";
import styled from "./page.module.css";
import Cover from "@/components/quizComponent/Cover";
export default function News() {
	return (
		<>
			<div className={styled["container"]}>
				<NewsDetail />
				<Cover />
			</div>
		</>
	);
}
