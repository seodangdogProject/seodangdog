package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Question {
    private String questionText;
    private Map<String, String> choices;
}
