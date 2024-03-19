package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class KeywordNews {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordNewsSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_seq")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword")
    private Keyword keyword;
}
