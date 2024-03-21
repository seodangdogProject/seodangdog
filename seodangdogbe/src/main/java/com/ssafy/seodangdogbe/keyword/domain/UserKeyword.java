package com.ssafy.seodangdogbe.keyword.domain;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserKeyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKeywordSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword")
    private Keyword keyword;

    private Long weight;
}