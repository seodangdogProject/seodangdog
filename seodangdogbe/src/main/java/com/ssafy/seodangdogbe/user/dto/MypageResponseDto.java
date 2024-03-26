package com.ssafy.seodangdogbe.user.dto;

import com.ssafy.seodangdogbe.news.dto.NewsPreviewListDto;
import com.ssafy.seodangdogbe.user.domain.UserExp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MypageResponseDto {
    private String nickname;
    private String userId;

    private UserAbilityDto ability;

    private String wordcloudImgUrl;

    private List<String> userBadgeNameList;
    private String badgeImgUrl;

//    private List<LocalDateTime> strictDateList;
    private List<Boolean> streakList;

    private NewsPreviewListDto recentViewNews;
    private NewsPreviewListDto recentSolvedNews;

    //newsSeq, newsImgUrl, newsTitle, newsDescription, newsCreatedAt, newsKeyword;

    @Getter
    @NoArgsConstructor
    public static class UserAbilityDto {
        private int wordAbility;        // 어휘 / 푼 뉴스 수
        private int inferenceAbility;   // 추론 / 푼 뉴스 수
        private int judgementAbility;   // 판단 / 푼 뉴스 수
        private int summaryAbility;     // 푼 뉴스 / 본 뉴스
        private int constantAbility;    // ** 스트릭 날짜 수 / 가입일자로부터 현재까지

        /*
            newExp: 본 뉴스 수
            wordExp: 어휘 정답개수
            inferenceExp: 추론 정답개수
            judgementExp: 판단 정답개수
            summaryExp: 요약 제출 개수 (= 푼 뉴스 수)
         */

        public UserAbilityDto(UserExp userExp){
            this.wordAbility = userExp.getWordExp() / userExp.getSummaryExp();
            this.inferenceAbility = userExp.getInferenceExp() / userExp.getSummaryExp();
            this.judgementAbility = userExp.getJudgementExp() / userExp.getSummaryExp();
            this.summaryAbility = userExp.getSummaryExp() / userExp.getNewsExp();
//            this.constantAbility = ;
        }
    }
}
