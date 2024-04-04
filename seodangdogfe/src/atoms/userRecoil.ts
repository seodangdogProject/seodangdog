import { atom } from "recoil";
import { ability, newsThumbnail, MyPageDto } from "@/atoms/type";

export const userExp = atom({
    key: "userKeywords",
    default: [],
});

export const id = atom({
    key: "userIdState",
    default: null,
});

export const mypageState = atom({
    key: "mypageState",
    default: null,
});

export const nicknameState = atom({
    key: "nicknameState",
    default: null,
});
