package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class newsWords {
    @Transient
    private String newsWord;
    @Transient
    private String pos;
}
