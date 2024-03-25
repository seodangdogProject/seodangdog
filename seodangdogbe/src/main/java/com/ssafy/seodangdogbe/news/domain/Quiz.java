package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.List;

@Getter
@Embeddable
public class Quiz {
    // 문제 번호
    private String quizNo;

    // 지시문
    private String statement;
    
    // 문제
    private List<String> description;

    // 선택지
    private List<String> options;

    // 정답
    private int answer;

}
