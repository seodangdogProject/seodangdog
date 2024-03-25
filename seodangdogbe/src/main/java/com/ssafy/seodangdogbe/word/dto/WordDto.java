package com.ssafy.seodangdogbe.word.dto;

import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.WordItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.seodangdogbe.word.dto.KorApiDto.*;

public class WordDto {

    /* ============================ metaWord dto ============================*/
    @Getter
    @NoArgsConstructor
    public static class MetaWordDto {
        private String word;
        private String wordLang;
        private int total;
        List<WordItemDto> items = new ArrayList<>();

        @Getter
        @NoArgsConstructor
        public static class WordItemDto {
            private int supNo;
            private String pos;
            private String definition;
            private String link;
//            private List<WordDetailsDto> details;     // 뜻 하나만 가져오려고했는데, 첫번째 뜻만 가져올거면 리스트로 가져올 필요가 없고, 중복
//            private List<WordCommDto> comms;          // comm_pattern_info > sense_info > lexical_info 라서 뜻 마다 연관어가 있는게 아니라서 위의 이유로 연관어들을 가져오더라도 몇번째 뜻의 연관어인지 구분할 수 X

            // 표준국어대사전 api 결과를 db에 저장하는 용도의 dto
            public WordItemDto(KorApiSearchDto.ItemDto dto){
                this.supNo = dto.getSupNo();
                this.pos = dto.getPos();
                this.definition = dto.getSense().getDefinition();
                this.link = dto.getSense().getLink();
            }

            // db MetaWord -> MetaWordDto
            public WordItemDto(WordItem wordItem){
                this.supNo = wordItem.getSupNo();
                this.pos = wordItem.getPos();
                this.definition = wordItem.getDefinition();
                this.link = wordItem.getLink();
            }
        }

//        public static class WordDetailsDto {
//            private String definition;
//            private List<WordCommDto> comms;
//        }

//        public static class WordCommDto {
//            private String type;
//            private String word;
//            private String link;
//        }

        // 표준국어대사전 api 결과를 db에 저장하는 용도의 dto
        public MetaWordDto(KorApiSearchDto korApiSearchDto){
            KorApiSearchDto.ChannelDto dto = korApiSearchDto.getChannel();

            this.wordLang = "kor";
            this.total = dto.getTotal();
            this.word = dto.getItem().get(0).getWord(); //첫번째로 가져온 item의 단어를 대표 단어로 저장

            List<KorApiSearchDto.ItemDto> items = dto.getItem();
            for (KorApiSearchDto.ItemDto item : items){
                this.items.add(new WordItemDto(item));
            }
        }

        // db MetaWord -> MetaWordDto
        public MetaWordDto(MetaWord metaWord){
            this.word = metaWord.getWord();
            this.wordLang = metaWord.getWordLang();
            this.total = metaWord.getTotal();
            List<WordItem> items = metaWord.getWordItemList();
            for (WordItem item : items){
                this.items.add(new WordItemDto(item));
            }
        }
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WordRequestDto {
        private String word;
    }

}
