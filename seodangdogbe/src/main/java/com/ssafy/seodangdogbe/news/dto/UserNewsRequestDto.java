package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.news.domain.Highlight;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserNewsRequestDto {
    private Long newsSeq;

    private List<Highlight> highlightList = new ArrayList<>();
    private List<Integer> wordList = new ArrayList<>();

    public UserNewsRequestDto(Long newsSeq, List<Highlight> highlightList, List<Integer> wordList) {
        this.newsSeq = newsSeq;
        this.highlightList = highlightList;
        this.wordList = wordList;
    }
}

