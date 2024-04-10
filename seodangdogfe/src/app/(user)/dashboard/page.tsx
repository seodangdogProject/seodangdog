"use client";
import DashboardContainer from "@/containers/DashboardContainer";
import { RecoilRoot } from "recoil";
export default function Dashboard() {
    return (
        <>
            <RecoilRoot>
                <DashboardContainer />
            </RecoilRoot>
        </>
    );
}
