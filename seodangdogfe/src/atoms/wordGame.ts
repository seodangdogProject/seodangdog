// recoilState.ts
import { atom } from "recoil";

export type GameItem = {
    wordSeq: number;
    isEng: boolean;
    word: string;
    mean: any;
};

export const gameWordListState = atom<GameItem[]>({
    key: "gamewordList",
    default: [],
});

export const correctWordListState = atom<GameItem[]>({
    key: "correctWordListState",
    default: [],
});

export const unCorrectWordListState = atom<GameItem[]>({
    key: "unCorrectWordListState",
    default: [],
});
