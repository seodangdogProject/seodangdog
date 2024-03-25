package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Ratings extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_seq")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private Double rating = 0.01;
}
