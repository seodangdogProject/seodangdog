package com.ssafy.seodangdogbe.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class NewsKeywordDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KeywordListRequestDto {
        private List<String> keywords;
    }
}
