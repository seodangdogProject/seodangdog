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
      <RecoilRoot>
        <main className={style.main}>
          <NavBar />
          <section className={style.section}>{children}</section>
        </main>
      </RecoilRoot>
    </>
  );
}

export default withAuth(RootLayout);
