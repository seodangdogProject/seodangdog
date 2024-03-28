package com.ssafy.seodangdogbe.keyword.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinKeyword {

    @Id
    private int joinKeywordSeq;

    private String keyword;

}
