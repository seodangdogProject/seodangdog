export type saved_word = {
    wordSeq: number;
    word: string;
    mean1: string;
    mean2: string;
};

export type ability = {
    wordAbility: number;
    inferenceAbility: number;
    judgementAbility: number;
    summaryAbility: number;
    constantAbility: number;
};

export type newsThumbnail = {
    newsSeq: number;
    newsImgUrl: string;
    newsTitle: string;
    newsDescription: string;
    newsCreatedAt: string;
    countView: number;
    newsKeyword: string[];
};

export type IPageObj = {
    pageNum: number;
};

export type MyPageDto = {
    nickname: string;
    userId: string;
    ability: ability;
    wordcloudImageUrl: string;
    userBadgeNewsList: string[];
    badgeImgUrl: string;
    streakList: number[];
    recentViewNews: newsThumbnail;
    recentSolvedNews: newsThumbnail;
};
