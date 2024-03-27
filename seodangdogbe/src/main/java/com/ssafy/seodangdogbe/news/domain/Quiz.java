package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.List;

@Getter
@Embeddable
public class Quiz {
    private String quizNo;

    private Question question;
    
    private Answer answer;


}
