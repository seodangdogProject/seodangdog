// recoilState.ts
import { atom } from "recoil";

export type Item = {
    idx: number;
    answer: string;
    mean: any;
};

// 테스트용
const testItems: Item[] = [
    { idx: 1, answer: "apple", mean: "사과" },
    { idx: 2, answer: "banana", mean: "바나나" },
    { idx: 3, answer: "orange", mean: "오렌지" },
];

export const gameWordListState = atom<Item[]>({
    key: "gamewordList",
    default: testItems,
});

export const correctWordListState = atom<Item[]>({
    key: "correctWordListState",
    default: testItems,
});

export const unCorrectWordListState = atom<Item[]>({
    key: "unCorrectWordListState",
    default: testItems,
});
