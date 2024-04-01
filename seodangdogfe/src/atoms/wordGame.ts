// recoilState.ts
import { atom } from "recoil";

export type Item = {
    wordSeq: number;
    word: string;
    mean: any;
};

export const gameWordListState = atom<Item[]>({
    key: "gamewordList",
    default: [],
});

export const correctWordListState = atom<Item[]>({
    key: "correctWordListState",
    default: [],
});

export const unCorrectWordListState = atom<Item[]>({
    key: "unCorrectWordListState",
    default: [],
});
