"use client";
import React, { useState, useEffect } from "react";
import { useRouter } from "next/router";
import { useRecoilState } from "recoil";
import { keywordsState } from "../../../atoms/joinRecoil";
import Link from "next/link";
import styles from "./gameIdPass_layout.module.css";

interface FallingLetter {
  id: number;
  keyword: string;
  x: number;
  y: number;
  isCaught: boolean;
  isShown: boolean;
  speed: number;
}

export default function JoinIdPass() {
  const router = useRouter();

  useEffect(() => {});

  return <div></div>;
}
