package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.news.domain.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
        private List<Boolean> correctList = new ArrayList<>();
        private UserSummary userSummary;
    }

    // 사용자 뉴스 본문 및 읽기내역(or 풀이내역) 조회용 dto
    @Getter
    @AllArgsConstructor
    public static class UserNewsResponseDto {
        @Setter
        private boolean isSolved = false;

        private List<Integer> highlightList = new ArrayList<>();
        private List<Integer> wordList = new ArrayList<>();
        @Setter
        private List<Integer> userAnswers = new ArrayList<>();
        @Setter
        private UserSummary userSummary = new UserSummary();

        public UserNewsResponseDto(List<Integer> highlightList, List<Integer> wordList) {
            this.highlightList = highlightList;
            this.wordList = wordList;
        }

    }
}

