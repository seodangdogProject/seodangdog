package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {

    @Id @GeneratedValue
//    @Column(name = "user_seq")    // 디폴트랑 똑같은 것 같은데 없어도 되나?
    private int userSeq;

    // 사용자 경험치
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_exp_seq")
    private UserExp userExp;

    // 사용자 대표 뱃지 ???
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badge;

    private String userId;

    private String password;
}
