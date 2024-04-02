"use client";
import RecommendNewsContainer from "@/containers/RecommendNewsContainer";
import { RecoilRoot } from "recoil";

export default function Main() {
  return (
    <>
      <RecoilRoot>
        <RecommendNewsContainer />
      </RecoilRoot>
    </>
  );
}
