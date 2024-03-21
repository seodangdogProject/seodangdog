package com.ssafy.seodangdogbe.word.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyWordResponseDto {
    private List<WordInfo> wordList;
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class WordInfo {
        private long wordSeq;
        private String word;
        private String mean;
    }

}
