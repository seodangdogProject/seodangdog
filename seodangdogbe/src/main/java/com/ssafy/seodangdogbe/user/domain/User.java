package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id @GeneratedValue
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

    // 양방향 연관관계 매핑 (주인X)
    @OneToMany(mappedBy = "user")
    private List<UserWord> userWords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserKeyword> userKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserBadge> userBadges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserMedia> userMediaList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserNews> userNewsList = new ArrayList<>();

}
