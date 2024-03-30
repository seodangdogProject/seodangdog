package com.ssafy.seodangdogbe.user.dto;

import com.ssafy.seodangdogbe.keyword.dto.UserKeywordDto;
import com.ssafy.seodangdogbe.news.dto.NewsPreviewListDto;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserExp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {
    private String nickname;
    private String userId;

    private UserAbilityDto ability;

    private List<BadgeDto> userBadgeList;
    private String badgeImgUrl;     // repBadgeImgUrl

    private List<LocalDate> streakList;
//    private List<Integer> streakCntList;

    private NewsPreviewListDto recentViewNews;
    private NewsPreviewListDto recentSolvedNews;

    private List<UserKeywordDto> wordCloudKeywords;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserAbilityDto {
        private float wordAbility = 0;        // 어휘 / 푼 뉴스 수
        private float inferenceAbility = 0;   // 추론 / 푼 뉴스 수
        private float judgementAbility = 0;   // 판단 / 푼 뉴스 수
        private float summaryAbility = 0;     // 푼 뉴스 / 본 뉴스
        private float constantAbility = 0;    // ** 스트릭 날짜 수 / 가입일자로부터 현재까지

        /*
            newExp: 본 뉴스 수
            wordExp: 어휘 정답개수
            inferenceExp: 추론 정답개수
            judgementExp: 판단 정답개수
            summaryExp: 요약 제출 개수 (= 푼 뉴스 수)
         */

        public UserAbilityDto(User user, int attendanceCount){
            UserExp userExp = user.getUserExp();
            this.wordAbility = (float) userExp.getWordExp() / userExp.getSummaryExp();
            this.inferenceAbility = (float) userExp.getInferenceExp() / userExp.getSummaryExp();
            this.judgementAbility = (float) userExp.getJudgementExp() / userExp.getSummaryExp();
            this.summaryAbility = (float) userExp.getSummaryExp() / userExp.getNewsExp();

            long duringDate = ChronoUnit.DAYS.between(user.getCreatedAt(), LocalDateTime.now());
            this.constantAbility = (float) attendanceCount / duringDate;
        }
    }
}
