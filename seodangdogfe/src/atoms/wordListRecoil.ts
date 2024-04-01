// recoilState.ts
import { atom } from "recoil";

export type Item = {
    wordSeq: number;
    word: string;
    mean1: string;
    mean2: string;
};

export const koWordListState = atom<Item[]>({
    key: "koWordListState",
    default: [],
});

export const engWordListState = atom<Item[]>({
    key: "engWordListState",
    default: [],
});
