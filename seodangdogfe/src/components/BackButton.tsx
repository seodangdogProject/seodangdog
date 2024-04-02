import classNames from "classnames/bind";
import styled from "./BackButton.module.css";
import { useRouter } from "next/navigation";
export default function BackButton() {
  const cx = classNames.bind(styled);
  const router = useRouter();
  return (
    <div onClick={() => router.back()} className={cx("back-container")}>
      <img src="/prev-arrow-icon.svg" alt="" />
    </div>
  );
}
