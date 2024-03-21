"use client";
import { useState, useEffect } from "react";

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

    // 페이지 로드 시 활동 상태 업데이트
    useEffect(() => {
        updateActivityMap(exampleDates);
    }, []);
    const recentDates: Date[] = [];
    const today = new Date();
    for (let i = 0; i < 30; i++) {
        const date = new Date(today);
        date.setDate(today.getDate() - i);
        recentDates.unshift(date); // 최신 날짜부터 추가
    }

    const getDaysOfWeek = (dates: Date[], dayOfWeek: number) => {
        const daysOfWeek: Date[] = [];
        dates.forEach((date) => {
            if (date.getDay() === dayOfWeek) {
                daysOfWeek.push(date);
            }
        });
        return daysOfWeek;
    };

    return (
        <div style={{ display: "flex" }}>
            {/* 날짜별 활동 표시 */}
            <div style={{ display: "flex", flexDirection: "column" }}>
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
                                        style={{
                                            width: "40px",
                                            height: "40px",
                                            backgroundColor: isActive
                                                ? "green"
                                                : "grey",
                                            border: "1px solid black",
                                            margin: "2px",
                                            position: "relative",
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
                                                    top: "-25px",
                                                    left: "50%",
                                                    transform:
                                                        "translateX(-50%)",
                                                    backgroundColor: "white",
                                                    padding: "2px",
                                                    borderRadius: "4px",
                                                    boxShadow:
                                                        "0 2px 4px rgba(0, 0, 0, 0.2)",
                                                }}
                                            >
                                                {`${date.getFullYear()}-${
                                                    date.getMonth() + 1
                                                }-${date.getDate()}`}
                                            </div>
                                        )}
                                    </div>
                                );
                            }
                        )}
                    </div>
                ))}
            </div>
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
                        style={{
                            width: "40px",
                            height: "20px",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            fontSize: "12px",
                        }}
                    >
                        {day}
                    </div>
                ))}
            </div>
        </div>
    );
}
