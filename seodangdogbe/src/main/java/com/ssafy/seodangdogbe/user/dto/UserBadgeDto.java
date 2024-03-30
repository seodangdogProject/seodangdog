package com.ssafy.seodangdogbe.user.dto;

import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;
import com.ssafy.seodangdogbe.user.domain.UserExp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBadgeDto {

    private BadgeDto badgeDto;
    private boolean isCollected = false;
    private boolean isRepresent = false;
    private int userBadgeExp;

    public UserBadgeDto(User user, Badge badge){
        this.badgeDto = new BadgeDto(badge);

        UserExp userExp = user.getUserExp();
        this.userBadgeExp = switch (badge.getBadgeName()) {
            case "어휘왕" -> userExp.getWordExp();
            case "추론왕" -> userExp.getInferenceExp();
            case "판단왕" -> userExp.getJudgementExp();
            case "요약왕" -> userExp.getSummaryExp();
            case "뉴스왕" -> userExp.getNewsExp();
            case "퀴즈왕" -> userExp.getWordGameExp();
            default -> 0;
        };
    }


    // from userBadge 테이블
    public UserBadgeDto(UserBadge userBadge) {
        this.badgeDto = new BadgeDto(userBadge.getBadge());
        UserExp userExp = userBadge.getUser().getUserExp();
        this.userBadgeExp = switch (userBadge.getBadge().getBadgeName()) {
            case "어휘왕" -> userExp.getWordExp();
            case "추론왕" -> userExp.getInferenceExp();
            case "판단왕" -> userExp.getJudgementExp();
            case "요약왕" -> userExp.getSummaryExp();
            case "뉴스왕" -> userExp.getNewsExp();
            case "퀴즈왕" -> userExp.getWordGameExp();
            default -> 0;
        };
        this.isCollected = true;
        this.isRepresent = userBadge.isRepBadge();
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
