"use client";
import NavBar from "@/components/NavBar";
import style from "./layout.module.css";
import { withAuth } from "@/hoc/withAuth";
import { Suspense } from "react";
function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <main className={style.main}>
        <NavBar />
        <section className={style.section}>{children}</section>
      </main>
    </>
  );
}

export default withAuth(RootLayout);
