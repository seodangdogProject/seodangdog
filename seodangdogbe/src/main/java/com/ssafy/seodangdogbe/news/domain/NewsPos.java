package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsPos {
    @Transient
    private String word;

    @Transient
    private String pos;
}
