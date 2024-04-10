package com.ssafy.seodangdogbe.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameGetProblemResponseDto {
    private List<WordInfo> wordList;

    @Data
    @AllArgsConstructor
    public static class WordInfo {
        private long wordSeq;
        private String word;
        private String mean;
        private Boolean isEng;
    }
}

