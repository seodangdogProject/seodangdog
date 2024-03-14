package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.media.domain.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class News {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsSeq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_code")
    private Media media;

    // mongodb 뉴스 접근 아이디
    @Column(length = 20)
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
