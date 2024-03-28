package com.ssafy.seodangdogbe.keyword.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinKeyword {
    @Id
    private int joinKeywordSeq;
    private String keyword;
}
