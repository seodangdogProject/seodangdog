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

    private Double weight;
<<<<<<< HEAD
=======

    public UserKeyword(User user, Keyword keyword, double weight) {
        this.user = user;
        this.keyword = keyword;
        this.weight = weight;
    }

    public UserKeyword(User user, String keyword, double weight) {
        this.user = user;
        this.keyword.setKeyword(keyword);
        this.weight = weight;
    }

>>>>>>> b9748078bc414d7eaec128448ff0bd130a17308e
}
