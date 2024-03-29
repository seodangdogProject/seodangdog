package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.media.domain.Media;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "meta_news") //mongodb collection명 (meta_news)
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MetaNews {

    @Id
    private String id;

    private String newsTitle;
    private List<String> newsSummary;
    private String newsCreatedAt;

    private String newsReporter;
    private String newsImgUrl;
    private String newsMainText;    // 뉴스 본문

    @Field("news_url")
    private String newsUrl;

    private Media media;    // media code, name

    // ** org.springframework.beans.BeanInstantiationException: Failed to instantiate [java.util.List]: Specified class is an interface
    private List<NewsPos> newsPos;    // word, pos
    private Map<String, Double> newsKeyword;
    private Map<String, Double> newsSummaryKeyword;

    @Field("newsQuiz")
    private List<Quiz> newsQuiz = new ArrayList<>();    // 퀴즈 1,2,3

}
