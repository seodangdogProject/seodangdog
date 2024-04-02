"use client";
import { useState, useEffect } from "react";
import style from "./strict_style.module.css";
// 예시로 사용할 날짜 데이터 배열

interface grassChart {
    dates: Map<string, number> | undefined;
}

const GrassChart = (props: grassChart) => {
    const [activityMap, setActivityMap] = useState<
        Map<string, number> | undefined
    >(new Map<string, number>());

    // 호버한 칸의 날짜를 저장할 상태 변수
    const [hoveredDate, setHoveredDate] = useState<string | null>(null);
    const weekDays = ["일", "월", "화", "수", "목", "금", "토"];

    // 활동 데이터를 기반으로 활동 상태를 업데이트하는 함수
    const updateActivityMap = (dates: Map<string, number> | undefined) => {
        const newActivityMap: Map<string, number> = new Map<string, number>();
        console.log("dates", dates);
        if (dates === undefined) {
            setActivityMap(new Map<string, number>());
        } else {
            for (const [key, value] of Object.entries(dates)) {
                newActivityMap.set(key, value);
            }

            setActivityMap(newActivityMap);
            console.log(newActivityMap);
        }
    };

    //  // 활동 데이터를 기반으로 활동 상태를 업데이트하는 함수
    //  const updateActivityMap = (dates: string[] | undefined) => {
    //     const newActivityMap: { [date: string]: boolean } = {};

    //     console.log("dates", dates);
    //     if (dates === undefined) {
    //         // setActivityMap();
    //     } else {
    //         dates.forEach((date) => {
    //             newActivityMap[date] = true;

    //         Array.prototype.forEach.call(dates,(value, key) => {
    //             newActivityMap[key] = value;
    //         });
    //         setActivityMap(newActivityMap);
    //     }
    // };

    useEffect(() => {
        updateActivityMap(props?.dates);
    }, [props?.dates]);

    const recentDates: Date[] = [];
    const today = new Date();

    // Calculate start date as the previous Saturday of the current week
    const startDate = new Date(today);
    startDate.setDate(today.getDate() + (6 - today.getDay())); // Set to Saturday of the current week

    // Generate recent dates for the past 11 weeks including the current week
    for (let i = 0; i < 7 * 11; i++) {
        const date = new Date(startDate);
        date.setDate(startDate.getDate() - i);
        recentDates.unshift(date); // Add dates starting from the past Saturday
    }

    const getDaysOfWeek = (dates: Date[], dayOfWeek: number) => {
        return dates.filter((date) => date.getDay() === dayOfWeek);
    };

    return (
        <div style={{ display: "flex", height: "100%" }}>
            {/* 날짜별 활동 표시 */}
            {/* 요일 표시 */}
            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    paddingTop: "10px",
                }}
            >
                {weekDays.map((day) => (
                    <div key={day} className={style.weekName}>
                        {day}
                    </div>
                ))}
            </div>

            <div style={{}} className={style.strictContainer}>
                {weekDays.map((day, index) => (
                    <div key={index} style={{ display: "flex" }}>
                        {getDaysOfWeek(recentDates, index).map(
                            (date, dateIndex) => {
                                const dateString = date
                                    .toISOString()
                                    .split("T")[0];
                                const count = activityMap?.get(dateString);
                                return (
                                    <div
                                        key={dateIndex}
                                        className={style.strictElement}
                                        style={{
                                            backgroundColor: count
                                                ? count === 1
                                                    ? "#dbb6ee" // count가 1인 경우
                                                    : count === 2
                                                    ? "#b57edc" // count가 2인 경우
                                                    : count >= 3
                                                    ? "#7f4ca5" // count가 3 이상인 경우
                                                    : "#fff0ff" // count가 0이 아니고, 1, 2, 3 이상도 아닌 경우
                                                : "#fff0ff", // count가 없는 경우
                                        }}
                                        onMouseEnter={() =>
                                            setHoveredDate(dateString)
                                        }
                                        onMouseLeave={() =>
                                            setHoveredDate(null)
                                        }
                                    >
                                        {hoveredDate === dateString && (
                                            <div
                                                className={style.hoverDock}
                                                style={{
                                                    color:
                                                        today.getMonth() ==
                                                            date.getMonth() &&
                                                        today.getDate() ==
                                                            date.getDate()
                                                            ? "red"
                                                            : "black",
                                                }}
                                            >
                                                {`${
                                                    date.getMonth() + 1
                                                }월${date.getDate()}일`}{" "}
                                                <br /> 푼 뉴스 수 :{" "}
                                                {count! >= 3
                                                    ? "3+"
                                                    : count! >= 1
                                                    ? count
                                                    : 0}
                                            </div>
                                        )}
                                    </div>
                                );
                            }
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default GrassChart;
