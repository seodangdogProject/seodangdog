package com.ssafy.seodangdogbe.word.dto;

import com.ssafy.seodangdogbe.word.domain.UserWord;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserWordDto {

    private Long wordSeq;
    private int userSeq;
    private String word;
    private boolean isDelete;

    public UserWordDto(UserWord userWord){
        this.wordSeq = userWord.getWordSeq();
        this.userSeq = userWord.getUser().getUserSeq();
        this.word = userWord.getWord();
        this.isDelete = userWord.isDelete();
    }

}
