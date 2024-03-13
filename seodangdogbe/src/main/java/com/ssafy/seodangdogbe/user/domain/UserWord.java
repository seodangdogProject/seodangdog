package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserWord {

    @Id @GeneratedValue
    private Long wordSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    // 단어 자체가 mongodb 단어 접근 아이디가 된다
    private String word;
}
