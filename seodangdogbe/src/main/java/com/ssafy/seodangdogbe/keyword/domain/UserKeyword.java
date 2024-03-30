package com.ssafy.seodangdogbe.keyword.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_keyword",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_seq", "keyword"})})
public class UserKeyword extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKeywordSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword")
    private Keyword keyword;

    private Double weight = 1.0;

    public UserKeyword(User user, String keyword) {
        this.user = user;
        this.keyword = new Keyword(keyword);
    }

    public UserKeyword(User user, String keyword, double weight) {
        this.user = user;
        this.keyword = new Keyword(keyword);
        this.weight = weight;
    }

}
