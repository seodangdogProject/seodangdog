'use client';
import { useState, useEffect } from 'react';

// 예시로 사용할 날짜 데이터 배열
const exampleDates = [
    '2024-03-01',
    '2024-03-02',
    '2024-03-05',
    '2024-03-07',
    '2024-03-08',
    '2024-03-11',
    '2024-03-12',
];

export default function GrassChart() {
    // 날짜별 활동 상태를 저장할 상태 변수
    const [activityMap, setActivityMap] = useState<{ [date: string]: boolean }>(
        {}
    );
    // 호버한 칸의 날짜를 저장할 상태 변수
    const [hoveredDate, setHoveredDate] = useState<string | null>(null);
    const weekDays = ['일', '월', '화', '수', '목', '금', '토'];

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

    // 오늘을 기준으로 최근 30일의 날짜 생성
    const recentDates: Date[] = [];
    const today = new Date();
    for (let i = 0; i < 30; i++) {
        const date = new Date(today);
        date.setDate(today.getDate() - i);
        recentDates.push(date);
    }

    // 주차별로 날짜를 묶어서 반환하는 함수
    const getWeeks = (dates: Date[]): Date[][] => {
        const weeks: Date[][] = [];
        let week: Date[] = [];
        dates.forEach((date) => {
            week.push(date);
            if (date.getDay() === 6) {
                // 토요일인 경우
                weeks.push([...week]);
                week = [];
            }
        });
        // 마지막 주의 날짜가 토요일이 아닌 경우 추가
        if (week.length > 0) {
            weeks.push([...week]);
        }
        return weeks;
    };
    return (
        <div style={{ display: 'flex', flexDirection: 'column' }}>
            {getWeeks(recentDates).map((week, weekIndex) => (
                <div key={weekIndex} style={{ display: 'flex' }}>
                    {week.map((date, index) => {
                        const dateString = date.toISOString().split('T')[0];
                        const isActive = activityMap[dateString];
                        return (
                            <div
                                key={index}
                                style={{
                                    width: '40px',
                                    height: '40px',
                                    backgroundColor: isActive
                                        ? 'green'
                                        : 'grey',
                                    border: '1px solid black',
                                    margin: '2px',
                                    position: 'relative',
                                }}
                                // 호버 이벤트 핸들러 추가
                                onMouseEnter={() => setHoveredDate(dateString)}
                                onMouseLeave={() => setHoveredDate(null)}
                            >
                                {/* 호버한 경우에만 날짜 정보를 표시 */}
                                {hoveredDate === dateString && (
                                    <div
                                        style={{
                                            position: 'absolute',
                                            top: '-25px',
                                            left: '50%',
                                            transform: 'translateX(-50%)',
                                            backgroundColor: 'white',
                                            padding: '2px',
                                            borderRadius: '4px',
                                            boxShadow:
                                                '0 2px 4px rgba(0, 0, 0, 0.2)',
                                        }}
                                    >
                                        {`${date.getFullYear()}-${
                                            date.getMonth() + 1
                                        }-${date.getDate()}`}
                                    </div>
                                )}
                            </div>
                        );
                    })}
                    {/* 각 주의 첫 번째 칸에 요일 표시 */}
                    {weekIndex === 0 && (
                        <div
                            style={{
                                width: '40px',
                                height: '20px',
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center',
                                fontSize: '12px',
                            }}
                        >
                            {week
                                .map((date) => weekDays[date.getDay()])
                                .join(' ')}
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
}
