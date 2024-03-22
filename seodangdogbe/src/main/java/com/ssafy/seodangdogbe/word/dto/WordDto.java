package com.ssafy.seodangdogbe.word.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WordDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class WordListResponseDto {
        private String word;
        private String definition;
        private String pos;
        private int total;
    }


    public static class WordDetailsResponseDto {
        private String word;
        private String definition;
        private String origin;

        private String pos;
        private String cat;

        private String com_type;
        private String com_word;
        private String com_link;


    }
}
