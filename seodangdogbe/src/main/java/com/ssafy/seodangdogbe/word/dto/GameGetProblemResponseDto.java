package com.ssafy.seodangdogbe.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor // 인자 없는 생성자
@AllArgsConstructor // 모든 인자를 받는 생성자
public class GameGetProblemResponseDto {
    private List<WordInfo> wordList;

    @Data
    @AllArgsConstructor
    public static class WordInfo {
        private long wordSeq;
        private String word;
        private String mean;
    }
}

