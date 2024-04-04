"use client";
import ReactDOM from "react-dom";
import React, { useEffect } from "react";
import style from "./wordCloud_style.module.css";
import ReactWordcloud, { Options, Scale } from "react-wordcloud";
import { keywordWeight } from "@/atoms/type";
import "tippy.js/dist/tippy.css";
import "tippy.js/animations/scale.css";

// 예시로 사용할 날짜 데이터 배열

interface wordCloudProps {
    wordCloudKeywords: keywordWeight[] | undefined;
}

const WordCloud = (props: wordCloudProps) => {
    useEffect(() => {}, []);
    const fontsize: [number, number] = [30, 80];
    const rotatingAngle: [number, number] = [-60, 60];
    const options: Options = {
        colors: [
            "#1f77b4",
            "#ff7f0e",
            "#2ca02c",
            "#d62728",
            "#9467bd",
            "#8c564b",
            "#FF7474",
            "#5868FF",
            "#FFA674",
            "#493971",
        ],
        enableTooltip: false,
        deterministic: true,
        fontFamily: "NanumMyeongjo",
        fontSizes: fontsize,
        fontStyle: "normal",
        fontWeight: "bolder",
        padding: 1,
        rotations: 20,
        rotationAngles: rotatingAngle,
        scale: "linear" as Scale,
        spiral: "archimedean",
        transitionDuration: 1000,
        enableOptimizations: true,
        svgAttributes: {},
        textAttributes: {},
        tooltipOptions: {},
    };

    return (
        <div style={{ backgroundColor: "white", height: "95%", width: "98%" }}>
            <ReactWordcloud
                words={props.wordCloudKeywords ? props.wordCloudKeywords : []}
                options={options}
            />
        </div>
    );
};

export default WordCloud;
