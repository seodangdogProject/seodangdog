package com.ssafy.seodangdogbe.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class GameResultRequestDto {
    private List<WordInfo> wordList;

    @Data
    @AllArgsConstructor
    public static class WordInfo {
        private Long wordSeq;
        private String word;

    }
}
