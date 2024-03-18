package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Id;

import java.util.List;

public class Quiz {
    
    // 문제
    private String example;
    
    // 문제 내용
    private List<String> content;

    // 정답
    private int answer;

}
