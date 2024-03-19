import styled from "./DashboardContainer.module.css";
import classNames from "classnames/bind";
function Info() {
  return <>asdf</>;
}
function Chart() {
  return <>1234</>;
}
function RecentPreview() {
  return <></>;
}
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
            <RecentPreview />
            <State />
          </div>
        </div>
      </div>
    </>
  );
}
