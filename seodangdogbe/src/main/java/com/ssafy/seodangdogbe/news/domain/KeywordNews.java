package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "keyword_news",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"news_seq", "keyword"})})
public class KeywordNews extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordNewsSeq;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne
    @JoinColumn(name = "news_seq")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
//  @ManyToOne
    @JoinColumn(name = "keyword")
    private Keyword keyword;
}
