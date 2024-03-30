package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    private Question question;
    private Answer answer;
}
