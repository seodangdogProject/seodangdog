"use client";
import { useState, useEffect } from "react";
import style from "./strict_style.module.css";
// 예시로 사용할 날짜 데이터 배열
const exampleDates = [
    "2024-03-01",
    "2024-03-02",
    "2024-03-05",
    "2024-03-07",
    "2024-03-08",
    "2024-03-11",
    "2024-03-12",
];

export default function GrassChart() {
    // 날짜별 활동 상태를 저장할 상태 변수
    const [activityMap, setActivityMap] = useState<{ [date: string]: boolean }>(
        {}
    );
    // 호버한 칸의 날짜를 저장할 상태 변수
    const [hoveredDate, setHoveredDate] = useState<string | null>(null);
    const weekDays = ["일", "월", "화", "수", "목", "금", "토"];

    // 활동 데이터를 기반으로 활동 상태를 업데이트하는 함수
    const updateActivityMap = (dates: string[]) => {
        const newActivityMap: { [date: string]: boolean } = {};
        dates.forEach((date) => {
            newActivityMap[date] = true;
        });
        setActivityMap(newActivityMap);
    };

    useEffect(() => {
        updateActivityMap(exampleDates);
    }, []);

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
        <div style={{ display: "flex" }}>
            {/* 날짜별 활동 표시 */}
            {/* 요일 표시 */}
            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                }}
            >
                {weekDays.map((day) => (
                    <div
                        key={day}
                        className={style.weekName}
                        style={{
                            width: "40px",
                            height: "25px",
                            display: "flex",
                            margin: "2px",

                            justifyContent: "center",
                            alignItems: "center",
                            fontSize: "12px",
                        }}
                    >
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
                                const isActive = activityMap[dateString];
                                return (
                                    <div
                                        key={dateIndex}
                                        className={style.strictElement}
                                        style={{
                                            backgroundColor: isActive
                                                ? "#37b328"
                                                : "#E2E2E2",
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
                                                style={{
                                                    position: "absolute",
                                                    display: "flex",
                                                    top: "-25px",
                                                    left: "50%",
                                                    width: "60px",
                                                    transform:
                                                        "translateX(-50%)",
                                                    backgroundColor: "white",
                                                    padding: "2px",
                                                    borderRadius: "4px",
                                                    boxShadow:
                                                        "0 2px 4px rgba(0, 0, 0, 0.2)",
                                                    color:
                                                        today.getMonth() ==
                                                            date.getMonth() &&
                                                        today.getDate() ==
                                                            date.getDate()
                                                            ? "red"
                                                            : "black",
                                                }}
                                            >
                                                {/* {today.getMonth() ==
                                                    date.getMonth() &&
                                                    today.getDate() ==
                                                        date.getDate() &&
                                                    "오늘"} */}
                                                {`${
                                                    date.getMonth() + 1
                                                }월${date.getDate()}일`}
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
}
