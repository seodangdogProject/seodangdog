package com.ssafy.seodangdogbe.news.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor
public class UserSummary {
    private String userSummaryContent;
    private List<String> userKeyword;
}
