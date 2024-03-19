// recoilState.ts
import { atom } from 'recoil';

export type Item = {
    idx: number;
    word: string;
    mean: string[];
};

// 테스트용
const testItems: Item[] = [
    {
        idx: 1,
        word: 'apple',
        mean: [
            '명사 고마움을 나타내는 인사',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 2,
        word: 'apple',
        mean: [
            '명사 고마움을 나타내는 인사',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 3,
        word: 'apple',
        mean: [
            '명사 고마움을 나타내는 인사',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 4,
        word: 'apple',
        mean: [
            '명사 고마움을 나타내는 인사',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 5,
        word: 'apple',
        mean: [
            '명사 고마움을 나타내는 인사',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
];

const engItems: Item[] = [
    {
        idx: 1,
        word: 'eng Apple',
        mean: [
            'Apples are often eaten fresh, cooked into dishes like pies or applesauce, or pressed to make juice.',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 2,
        word: 'eng Apple',
        mean: [
            'Apples are often eaten fresh, cooked into dishes like pies or applesauce, or pressed to make juice.',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 3,
        word: 'eng Apple',
        mean: [
            'Apples are often eaten fresh, cooked into dishes like pies or applesauce, or pressed to make juice.',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 4,
        word: 'eng Apple',
        mean: [
            'Apples are often eaten fresh, cooked into dishes like pies or applesauce, or pressed to make juice.',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
    {
        idx: 5,
        word: 'eng Apple',
        mean: [
            'Apples are often eaten fresh, cooked into dishes like pies or applesauce, or pressed to make juice.',
            '명사 단체의 서무를 맡아보는 직책. 또는 그 직책에 있는 사람.',
        ],
    },
];

export const koWordListState = atom<Item[]>({
    key: 'koWordListState',
    default: testItems,
});

export const engWordListState = atom<Item[]>({
    key: 'engWordListState',
    default: engItems,
});
