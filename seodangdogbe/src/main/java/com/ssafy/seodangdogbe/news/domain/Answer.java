package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Answer {
    private Object number;
    private String reason;
}
