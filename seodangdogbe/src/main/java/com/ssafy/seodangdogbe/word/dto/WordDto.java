package com.ssafy.seodangdogbe.word.dto;

import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.WordItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.seodangdogbe.word.dto.WordApiDto.*;

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

            // 표준국어대사전 api 결과를 db에 저장하는 용도의 dto
            public WordItemDto(KorApiSearchDto.ItemDto dto){
                this.supNo = dto.getSupNo();
                this.pos = dto.getPos();
                this.definition = dto.getSense().getDefinition();
                this.link = dto.getSense().getLink();
            }

            // 네이버 백과사전 api 결과를 db에 저장하는 용도의 dto
            public WordItemDto(EncycApiDto.ItemDto dto, int idx){
                this.supNo = idx;
                this.pos = dto.getTitle();    // 품사 X => 검색결과로 나타나는 단어 반환
                this.definition = dto.getDescription();
                this.link = dto.getLink();
            }

            // db MetaWord -> MetaWordDto
            public WordItemDto(WordItem wordItem){
                this.supNo = wordItem.getSupNo();
                this.pos = wordItem.getPos();
                this.definition = wordItem.getDefinition();
                this.link = wordItem.getLink();
            }
        }

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


        // 네이버 백과사전 api 결과를 db에 저장하는 용도의 dto
        public MetaWordDto(String word, EncycApiDto encycApiDto, String wordLang) {
            EncycApiDto dto = encycApiDto;

            this.wordLang = wordLang;
            this.total = dto.getTotal();
            this.word = word;

            List<EncycApiDto.ItemDto> items = dto.getItems();
            int idx = 1;
            for (EncycApiDto.ItemDto item : items){
                this.items.add(new WordItemDto(item, idx++));
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
