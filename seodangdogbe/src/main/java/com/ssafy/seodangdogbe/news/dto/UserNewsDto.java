package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.media.domain.Media;
import com.ssafy.seodangdogbe.news.domain.Quiz;
import com.ssafy.seodangdogbe.news.domain.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserNewsDto {

    // 사용자 뉴스 읽기 기록 저장용 dto
    @Getter
    @AllArgsConstructor
    public static class UserNewsReadRequestDto {
        private Long newsSeq;
        private List<Integer> highlightList = new ArrayList<>();
        private List<Integer> wordList = new ArrayList<>();
    }

    // 사용자 뉴스 풀이 기록 저장용 dto
    @Getter
    @AllArgsConstructor
    public static class UserNewsSolveRequestDto {
        private Long newsSeq;
        private List<Integer> userAnswerList = new ArrayList<>();
        private UserSummary userSummary;
    }

    // 사용자 뉴스 본문 및 읽기내역(or 풀이내역) 조회용 dto
    @Getter
    @AllArgsConstructor
    public static class UserNewsResponseDto {
//        private String newsTitle;
//        private List<String> newsSummary;
//        private LocalDateTime newsCreatedAt;
//
//        private String newsReporter;
//        private String newsImgUrl;
//        private String newsMainText;
//
//        private Media media;
//
//        private List<Keyword> newsKeyword;
//        private List<Quiz> newsQuiz;

        private List<Integer> highlightList = new ArrayList<>();
        private List<Integer> wordList = new ArrayList<>();
        private List<Integer> userAnswers = new ArrayList<>();
        private UserSummary userSummary;
    }
}

