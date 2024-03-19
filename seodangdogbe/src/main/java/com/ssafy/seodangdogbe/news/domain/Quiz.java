package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.List;

@Getter
@Embeddable
public class Quiz {
    
    // 문제
    private String example;
    
    // 문제 내용
    @ElementCollection
    private List<String> content;

    // 정답
    private int answer;

}
