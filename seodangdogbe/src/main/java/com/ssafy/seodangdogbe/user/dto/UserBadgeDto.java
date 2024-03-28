package com.ssafy.seodangdogbe.user.dto;

import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserExp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBadgeDto {

    private BadgeDto badgeDto;
    private boolean isCollected;
    private boolean isRepresent = false;
    private int userBadgeExp;

    public UserBadgeDto(User user, Badge badge){
        UserExp userExp = user.getUserExp();
        this.badgeDto = new BadgeDto(badge);
        int exp = switch (badge.getBadgeName()) {
            case "어휘왕" -> userExp.getWordExp();
            case "추론왕" -> userExp.getInferenceExp();
            case "판단왕" -> userExp.getJudgementExp();
            case "요약왕" -> userExp.getSummaryExp();
            case "뉴스왕" -> userExp.getNewsExp();
            case "퀴즈왕" -> userExp.getWordGameExp();
            default -> 0;
        };

        this.isCollected = badge.getBadgeCondition() <= exp;
        this.isRepresent = user.getBadge().getBadgeSeq() == badge.getBadgeSeq();
        this.userBadgeExp = exp;
    }

    @Getter
    @AllArgsConstructor
    public static class UserExpDto {
        private int newsExp;    //본 뉴스 개수
        private int wordExp;    //어휘 문제 정답 개수
        private int inferenceExp;   //추론 문제 정답 개수
        private int judgementExp;   //판단 문제 정답 개수
        private int summaryExp;     //요약 작성 개수
        private int wordGameExp;    //단어 게임 정답 개수

        public UserExpDto(UserExp userExp){
            this.newsExp = userExp.getNewsExp();
            this.wordExp = userExp.getWordExp();
            this.inferenceExp = userExp.getInferenceExp();
            this.judgementExp = userExp.getJudgementExp();
            this.summaryExp = userExp.getSummaryExp();
            this.wordGameExp = userExp.getWordGameExp();
        }
    }
}
