package com.ssafy.seodangdogbe.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResultRequestDto {
    private int userSeq;
    private List<WordInfo> wordList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WordInfo {
        private Long wordSeq;
        private String word;

    }
}
