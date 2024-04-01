package com.ssafy.seodangdogbe.word.domain;


import com.ssafy.seodangdogbe.word.dto.WordDto;
import com.ssafy.seodangdogbe.word.dto.WordDto.MetaWordDto;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.seodangdogbe.word.dto.WordDto.MetaWordDto.*;

// metaWord - wordItem - wordDetails - commWord
@Document(collection = "meta_words") //mongodb collection명 (meta_news)
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaWord {
    @Id
    private String id;

    @Indexed(name = "word", unique = true)
    private String word;

    private String wordLang;
    private int total;

    private List<WordItem> wordItemList = new ArrayList<>();

    public MetaWord(String word, String wordLang, int total, List<WordItem> wordItemList) {
        this.word = word.replaceAll("[!@#$%^&*(){}\\[\\]_\\-+=?/\\\\]", "");
        this.wordLang = wordLang;
        this.total = total;
        this.wordItemList = wordItemList;
    }

    public MetaWord(MetaWordDto dto){
        this.word = dto.getWord().replaceAll("[!@#$%^&*(){}\\[\\]_\\-+=?/\\\\]", "");
        this.wordLang = dto.getWordLang();
        this.total = dto.getTotal();
        for (WordItemDto item : dto.getItems()){
            this.wordItemList.add(new WordItem(item));
        }
    }
}
