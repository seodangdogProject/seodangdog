"use client";
import { useState, useEffect } from "react";
import style from "./chart_style.module.css";
import {
  Chart as ChartJS,
  LineController,
  LineElement,
  PointElement,
  LinearScale,
  Title,
  RadialLinearScale,
} from "chart.js";
import { Radar } from "react-chartjs-2";

export default function ExpChart() {
  // const options = {
  //     scale: {
  //         angleLines: {
  //             display: false,
  //         },
  //         ticks: {
  //             suggestedMin: 50,
  //             suggestedMax: 100,
  //         },
  //     },
  // };

  const chartData = {
    labels: ["어휘", "판단", "추론", "요약", "성실"],
    datasets: [
      {
        label: "내 능력치",
        data: [6.6, 5.2, 9.1, 5.6, 5.5],
        backgroundColor: "rgba(255, 108, 61, 0.2)",
      },
    ],
  };

  const options = {
    responsive: true,
    scale: {
      beginAtZero: true,
      max: 100,
      stepSize: 10,
    },
    elements: {
      line: {
        borderWidth: 3,
      },
    },
  };

  const data = {
    labels: ["어휘", "판단", "추론", "요약", "성실"],
    datasets: [
      {
        label: "D0",
        fill: true,
        data: [66, 52, 91, 56, 55],
        backgroundColor: "rgba(54, 162, 235, 0.2)",
        borderColor: "rgb(54, 162, 235)",
        pointBackgroundColor: "rgb(54, 162, 235)",
        pointBorderColor: "#fff",
        pointHoverBackgroundColor: "#fff",
        pointHoverBorderColor: "rgb(54, 162, 235)",
      },
    ],
  };

  ChartJS.register(
    LineController,
    LineElement,
    PointElement,
    LinearScale,
    Title,
    RadialLinearScale
  );

  return (
    <>
      <Radar options={options} data={data} />
    </>
  );
}
