import { atom } from 'recoil';

export const userExp = atom({
    key: 'userKeywords',
    default: [],
});

export const id = atom({
    key: 'userIdState',
    default: null,
});
