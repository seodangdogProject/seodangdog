package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.user.domain.User;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Getter @Setter
public class UserNews {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNewsSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_seq")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private boolean isSolved;   // 푼문제 여부. 푼문제가 아니라면 접근만 한 문제

    // 형광펜 목록
    @Column(name = "highlight_list", columnDefinition = "json")
    @Type(JsonType.class)
    private List<Integer> highlightList;

    // 단어 목록
    @Column(name = "word_list", columnDefinition = "json")
    @Type(JsonType.class)
    private List<Integer> wordList;

    // 정답 제출 목록
    @Column(name = "user_answer_list", columnDefinition = "json")
    @Type(JsonType.class)
    private List<Integer> userAnswerList;

    // 사용자 요약(요약, 주요키워드)
    @Column(name = "user_summary", columnDefinition = "json")
    @Type(JsonType.class)
    private UserSummary userSummary;
}
