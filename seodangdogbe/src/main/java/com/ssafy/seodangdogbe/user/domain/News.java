package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class News {

    @Id @GeneratedValue
    private Long newsSeq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_code")
    private Media media;

    // mongodb 뉴스 접근 아이디
    private String newsAccessId;

    private int countSolve;
    private int countView;

    private String newsImgUrl;
    private String newsTitle;
    private String newsDescription;
    private LocalDateTime newsCreatedAt;

    @OneToMany(mappedBy = "news")
    private List<KeywordNews> keywordNewsList = new ArrayList<>();


}
