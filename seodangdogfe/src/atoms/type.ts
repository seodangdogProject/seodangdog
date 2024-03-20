export type saved_word = {
    idx: number;
    word: string;
    mean: string[];
};

// 사용자 경험치 - type 당 exp 값
export type ability = {
    word_exp: number; // 어휘 경험치
    inference_exp: number; // 추론 경험치
    judgement_exp: number; // 판단 경험치
    summary_exp: number; // 요약 경험치 -> 푼 뉴스 / 본 뉴스
    constant_exp: number; // 성실 경험치 -> 뉴스를 푼 날짜 수 / 가입 D+Day
};

export type IPageObj = {
    pageNum: number;
};
