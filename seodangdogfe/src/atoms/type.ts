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

export type RefreshReq = {
    newsSeq: number;
    keywordInfoList: KeywordInfo[];
};

export type KeywordInfo = {
    keyword: string;
    weight: number | undefined;
};

export type newsDetailThumbnail = {
    newsSeq: number;
    newsImgUrl: string;
    newsTitle: string;
    newsDescription: string;
    newsCreatedAt: string;
    countView: number;
    media: string;
    newsKeyword: string[];
};

export type IPageObj = {
    pageNum: number;
};

export type MyPageDto = {
    nickname: string;
    userId: string;
    ability: ability;
    wordCloudKeywords: keywordWeight[];
    userBadgeList: badge[];
    badgeImgUrl: string;
    streakList: string[];
    recentViewNews: newsThumbnail;
    recentSolvedNews: newsThumbnail;
};

export type badge = {
    badgeSeq: number;
    badgeName: String;
    badgeImgUrl: string;
    badgeDescription: string;
    badgeCondition: number; // 달성 해야하는 갯수
};

export type badgeInfo = {
    badgeDto: badge;
    userBadgeExp: number;
    represent: boolean;
    collected: boolean;
};

export type keywordWeight = {
    text: string;
    value: number;
};

export type NewsData = {
    countView: number;
    media: string;
    newsCreatedAt: string;
    newsDescription: string;
    newsImgUrl: string;
    newsKeyword: Map<string, number>;
    newsSeq: number;
    newsTitle: string;
};
