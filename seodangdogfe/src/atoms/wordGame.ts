// recoilState.ts
import { atom } from 'recoil';

export type Item = {
    idx: number;
    answer: string;
    mean: any;
};

export const wordListState = atom<Item[]>({
    key: 'wordListState',
    default: [],
});
