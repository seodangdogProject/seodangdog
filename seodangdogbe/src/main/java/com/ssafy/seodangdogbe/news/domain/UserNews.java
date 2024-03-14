package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.user.domain.User;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
public class UserNews {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNewsSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_seq")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private boolean isSolved;

    // mongodb 사용자-뉴스 기록 접근 아이디
    @Column(length = 20)
    private String userNewsAccessId;

    @Column(name = "highlight_list", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)    // @JdbcTypeCode(SqlTypes.JSON) 이거랑 무슨 차이지?
    private Map<String, String> highlightList;

    @Column(name = "word_list", columnDefinition = "json")
    @Type(JsonType.class)
    private Map<String, String> wordList;

    @Column(name = "answer_list", columnDefinition = "json")
    @Type(JsonType.class)
    private Map<String, String> answerList;

    @Column(name = "summary", columnDefinition = "json")
    @Type(JsonType.class)
    private Map<String, String> summary;
}
