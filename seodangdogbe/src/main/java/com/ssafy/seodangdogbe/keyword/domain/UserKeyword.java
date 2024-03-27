package com.ssafy.seodangdogbe.keyword.domain;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserKeyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKeywordSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword")
    private Keyword keyword;

    private Double weight = 3.0;

    public UserKeyword(User user, String keyword, double weight) {
        this.user = user;
        this.keyword = new Keyword(keyword);
        this.weight = weight;
    }

}
