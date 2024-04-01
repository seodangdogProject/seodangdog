"use client";
import NavBar from "@/components/NavBar";
import style from "./layout.module.css";
import { withAuth } from "@/hoc/withAuth";
import { Suspense } from "react";
import { RecoilRoot } from "recoil";
function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <main className={style.main}>
        <RecoilRoot>
          <NavBar />
        </RecoilRoot>
        <section className={style.section}>{children}</section>
      </main>
    </>
  );
}

export default withAuth(RootLayout);
