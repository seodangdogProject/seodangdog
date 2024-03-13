package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UserBadge")  // 디폴트랑 똑같으면 안해도 되는거 아녀?
@Getter
@Setter
public class UserBadge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userBadgeSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badge;
}
