package com.ssafy.seodangdogbe.user.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
public class UserNews {

    @Id @GeneratedValue
    private Long userNewsSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_seq")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private boolean isSolved;

    private String userNewsAccessId;

    @Column(name = "highlight_list", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> highlightList;

    @Column(name = "word_list", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> wordList;

    @Column(name = "answer_list", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> answerList;

    @Column(name = "summary", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> summary;
}
