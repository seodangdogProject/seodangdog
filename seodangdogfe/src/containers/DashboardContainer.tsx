import styled from "./DashboardContainer.module.css";
import classNames from "classnames/bind";
import RecentNewsPreview from "@/components/dashboard/RecentNewsPreview";
function Info() {
  return <>Info</>;
}
function Chart() {
  return <>Chart</>;
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
          <div className={cx("dashboard__first")}>
            <div className={cx("dashboard__second", ["bg-light-purple"])}>
              <Info />
            </div>
            <div className={cx("dashboard__second", ["bg-light-purple"])}>
              <Chart />
            </div>
          </div>
          {/* 두 번째 */}
          <div className={cx("dashboard__first", "$bg__light__purple")}>
            <div
              className={cx("dashboard__second", ["bg-light-purple"], "recent")}
            >
              <RecentNewsPreview />
            </div>
            <div
              className={cx("dashboard__second", ["bg-light-purple"], "state")}
            >
              <State />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
