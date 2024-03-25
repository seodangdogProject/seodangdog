"use client";
import { useRouter } from "next/navigation.js";
import { useEffect, useState } from "react";
import Loading from "./loading.tsx";
// @ts-ignore
// eslint-disable-next-line react/display-name
export const withAuth = (Component) => (props) => {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true); // 로딩 상태

  /* 권한 분기 */
  useEffect(() => {
    if (!localStorage.getItem("accessToken")) {
      router.replace("/landing");
    } else {
      setIsLoading(false);
    }
  }, []);
  if (isLoading) {
    return <Loading />;
  } else {
    return <Component {...props} />;
  }
};
