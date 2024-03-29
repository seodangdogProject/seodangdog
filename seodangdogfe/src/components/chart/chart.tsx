"use client";
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
import { ability } from "@/atoms/type";

interface chartProps {
    ability: ability | undefined;
}

const Chart = (props: chartProps) => {
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

    let data;
    if (props.ability?.constantAbility == undefined) {
        data = {
            labels: ["어휘", "판단", "추론", "요약", "성실"],
            datasets: [
                {
                    label: "D0",
                    fill: true,
                    data: [0, 0, 0, 0, 0],
                    backgroundColor: "rgba(54, 162, 235, 0.2)",
                    borderColor: "rgb(54, 162, 235)",
                    pointBackgroundColor: "rgb(54, 162, 235)",
                    pointBorderColor: "#fff",
                    pointHoverBackgroundColor: "#fff",
                    pointHoverBorderColor: "rgb(54, 162, 235)",
                },
            ],
        };
    } else {
        data = {
            labels: ["어휘", "판단", "추론", "요약", "성실"],
            datasets: [
                {
                    label: "D0",
                    fill: true,
                    data: [
                        props.ability?.wordAbility * 100,
                        props.ability?.judgementAbility * 100,
                        props.ability?.inferenceAbility * 100,
                        props.ability?.summaryAbility * 100,
                        props.ability?.constantAbility * 100,
                    ],
                    backgroundColor: "rgba(54, 162, 235, 0.2)",
                    borderColor: "rgb(54, 162, 235)",
                    pointBackgroundColor: "rgb(54, 162, 235)",
                    pointBorderColor: "#fff",
                    pointHoverBackgroundColor: "#fff",
                    pointHoverBorderColor: "rgb(54, 162, 235)",
                },
            ],
        };
    }

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
};

export default Chart;
