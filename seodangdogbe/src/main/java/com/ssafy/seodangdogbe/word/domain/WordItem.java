package com.ssafy.seodangdogbe.word.domain;

import com.ssafy.seodangdogbe.word.dto.WordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.seodangdogbe.word.dto.WordDto.*;
import static com.ssafy.seodangdogbe.word.dto.WordDto.MetaWordDto.*;

// metaWord - wordItem - wordDetails - commWord
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WordItem {
    private int supNo;
    private String pos;
    private String definition;
    private String link;

    public WordItem(WordItemDto wordItemDto){
        this.supNo = wordItemDto.getSupNo();
        this.pos = wordItemDto.getPos();
        this.definition = wordItemDto.getDefinition();
        this.link = wordItemDto.getLink();
    }
}
