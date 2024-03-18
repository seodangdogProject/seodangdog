import { atom } from "recoil";

export const userKeywords = atom({
  key: "userKeywords",
  default: [],
});

export const id = atom({
  key: "userIdState",
  default: null,
});
