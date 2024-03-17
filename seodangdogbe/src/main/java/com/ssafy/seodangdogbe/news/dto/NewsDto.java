package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.media.domain.Media;
import com.ssafy.seodangdogbe.news.domain.Quiz;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "News")
public class NewsDto {
    @Id // mongodb에서 자동으로 생성한 id
    private Long newsSeq;

    private String newsReporter;
    private String newsImgUrl;
    private String newsTitle;
    private String newsMainText;
    private LocalDateTime newsCreatedAt;

    private List<Keyword> newsKeyword;
    private List<String> newsSummary;
    private List<Media> media;

    private List<Quiz> newsQuiz;

}
