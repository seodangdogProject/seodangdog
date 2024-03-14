package com.ssafy.seodangdogbe.log.domain;

import jakarta.persistence.*;

@Entity
public class KeywordWeightLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordWeightLogSeq;

    private Long newsSeq;
    private int userSeq;
    private Long keywordSeq;

    @Column(length = 20)
    private String content;

    // 원래 Byte(= tiny int)
    private int difference;

    private int total;

}
