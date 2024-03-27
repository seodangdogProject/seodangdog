import { atom } from "recoil";
import { ability, newsThumbnail } from "@/atoms/type";

export const userExp = atom({
    key: "userKeywords",
    default: [],
});

export const id = atom({
    key: "userIdState",
    default: null,
});

export const mypageRecoil = atom({
    key: "mypageRecoil",
});
