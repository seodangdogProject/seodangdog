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
import { Line, Radar } from "react-chartjs-2";
import { ability } from "@/atoms/type";
import styles from "./chart_style.module.css";

interface chartProps {
    ability: ability | undefined;
}

const Chart = (props: chartProps) => {
    const options = {
        responsive: true,
        scale: {
            beginAtZero: true,
            max: 100,
            stepSize: 20,
        },
        elements: {
            point:{
                radius: 3,
                pointStyle: 'circle'
            },
            line: {
                borderWidth: 3,
                borderCapStyle: 'round',
                borderJoinStyle: "round",
            },
        },
    };

    let data;
    if (props.ability?.constantAbility == undefined) {
        data = {
            labels: ["어휘", "판단", "추론", "요약", "성실"],
            datasets: [
                {
                    fill: true,
                    data: [0, 0, 0, 0, 0],
                    backgroundColor: "rgba(54, 162, 235, 0.2)",
                    borderColor: "#FF7474",
                    pointBackgroundColor: "#F1DEF0",
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
                    label: '내 경험치',
                    data: [
                        props.ability?.wordAbility * 100,
                        props.ability?.judgementAbility * 100,
                        props.ability?.inferenceAbility * 100,
                        props.ability?.summaryAbility * 100,
                        props.ability?.constantAbility * 100,
                    ],
                    fill: true,
                    // backgroundColor: "rgba(54, 162, 235)",
                    // line
                    borderColor: "#FF7474",
                    // point
                    // pointStyle: 'circle',
                    pointBackgroundColor: "#FF7474",
                    pointBorderColor: "#FF7474",
                    pointBorderWidth: 1,
                    pointHoverRadius: 4,
                    pointHoverBackgroundColor: "#fff",
                    pointHoverBorderColor: "#FF7474",
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
            <div
                className={styles.container}
                style={{ width: "100%", height: "100%", overflow: "hidden" }}
            >
                <Radar options={options} data={data} />
            </div>
        </>
    );
};

export default Chart;
