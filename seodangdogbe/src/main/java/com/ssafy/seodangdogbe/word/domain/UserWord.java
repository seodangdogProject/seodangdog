package com.ssafy.seodangdogbe.word.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_word",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_seq", "word"})})
public class UserWord extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    // 단어 자체가 mongodb 단어 접근 아이디가 된다
    @Column(length = 50)
    private String word;

    public UserWord(User user, String word){
        this.user = user;
        this.word = word;
    }

}
