package com.ssafy.seodangdogbe.user.domain;

import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.media.domain.UserMedia;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    // 사용자 경험치
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_exp_seq")
    private UserExp userExp;

    // 사용자 대표 뱃지 ???
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badge;

    @Column(length = 15)
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


    public User(int userSeq){
        this.userSeq = userSeq;
    }
}
