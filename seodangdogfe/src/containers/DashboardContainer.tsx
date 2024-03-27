import styled from "./DashboardContainer.module.css";
import classNames from "classnames/bind";
import RecentNewsPreview from "@/components/dashboard/RecentNewsPreview";
import UserCard from "@/components/dashboard/UserCard";
import Strict from "@/components/strict/strict";
import Chart from "@/components/chart/chart";
function Info() {
  return <></>;
}

// function RecentPreview() {
//   return <></>;
// }
function State() {
  return <></>;
}
export default function DashboardContainer() {
  const cx = classNames.bind(styled);
  return (
    <>
      <div className={cx("container")}>
        <div className={cx("dashboard")}>
          <div className={cx("dashboard__top")}>
            <div
              className={cx("dashboard__top__first", "padding-item", [
                "bg-light-purple",
              ])}
            >
              <div className={styled.title}>Info</div>
              <div className={cx("user", ["box-shodow-custom"])}>
                <UserCard />
              </div>
              <div className={cx("streak", ["box-shodow-custom"])}>
                <Strict></Strict>
              </div>
            </div>
            <div
              className={cx("dashboard__top__second", "padding-item", [
                "bg-light-purple",
              ])}
            >
              <div className={styled.title}>워드클라우드</div>
              <div className={cx("wordcloud", ["box-shodow-custom"])}>
                <div className={cx("boxshadow", ["box-shodow-custom"])}>
                  {/* 여기에 워드클라우드 컴포넌트 */}
                </div>
              </div>
            </div>
          </div>
          {/* 두 번째 */}
          <div className={cx("dashboard__bottom", "bg__light__purple")}>
            <div
              className={cx(
                "dashboard__bottom__first",
                ["bg-light-purple"]
                // "padding-item"
              )}
            >
              <RecentNewsPreview />
            </div>
            <div
              className={cx(
                "dashboard__bottom__second",
                ["bg-light-purple"],
                "padding-item"
              )}
            >
              <div className={styled.title}>능력치</div>
              <div className={cx("state", ["box-shodow-custom"])}>
                <Chart />
              </div>
              <State />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
