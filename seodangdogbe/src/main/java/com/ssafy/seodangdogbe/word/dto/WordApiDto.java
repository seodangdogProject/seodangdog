package com.ssafy.seodangdogbe.word.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class WordApiDto {

    /* ============================ 표준국어대사전 검색 api 결과 ============================*/
    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KorApiSearchDto {
        private ChannelDto channel;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ChannelDto {
            private int total;
            private List<ItemDto> item;
        }

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ItemDto {
            @JsonProperty("sup_no")
            private int supNo;
            private String word;
            private String pos;
            private SenseDto sense;
        }

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SenseDto {
            private String definition;
            private String link;
        }
    }


    /* ============================ 네이버 백과사전 검색 api 결과 ============================*/
    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EncycApiDto {
        private int total;
        private List<ItemDto> items;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ItemDto {
            private String title;
            private String link;
            private String description;
        }

    }


    /* ============================ 표준국어대사전 내용 api ============================*/
    // 검색한 단어 => 뜻 여러개 => 그 중 하나에 들어가면 또 뜻이 여러개 있음. 근데 이거 안써서 '사전내용 api' 아예 안써도 됨.
//    public static class KorApiViewDto {
//        private ChannelDto channel;
//
//        public static class ChannelDto {
//            private ItemDto item;
//        }
//
//        public static class ItemDto {
//            @JsonProperty("word_info")
//            private WordInfoDto wordInfo;
//        }
//
//        public static class WordInfoDto {
//            private String word;
//
//            @JsonProperty("pos_info")
//            private List<PosInfoDto> posInfo;
//        }
//
//        public static class PosInfoDto {
//            private String pos;
////            @JsonProperty("comm_pattern_info")
////            private List<CommPatternInfoDto> commPatternInfo;
//        }
//
//        // 연관어 안할래요.
////        public static class CommPatternInfoDto {
////            @JsonProperty("sense_info")
////            private List<SenseInfoDto> senseInfo;
////        }
////
////        public static class SenseInfoDto {
//////            private String definition;
////            @JsonProperty("lexical_info")
////            private List<LexicalInfoDto> lexicalInfo;
////        }
////
////        public static class LexicalInfoDto {
////            private String word;
////            private String type;
////            private String link;
////        }
//    }
}
