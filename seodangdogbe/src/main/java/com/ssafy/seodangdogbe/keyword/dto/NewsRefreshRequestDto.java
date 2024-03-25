package com.ssafy.seodangdogbe.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewsRefreshRequestDto {
    private Long newsSeq;
    private List<NewsKeyword> keyword;

    @Data
    @AllArgsConstructor
    public static class NewsKeyword {
        private String newskeyword;

    }
}
