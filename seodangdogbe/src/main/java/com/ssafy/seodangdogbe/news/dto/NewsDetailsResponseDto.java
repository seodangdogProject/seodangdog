package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.media.domain.Media;
import com.ssafy.seodangdogbe.news.domain.MetaNews;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class NewsDetailsResponseDto {
    private Long newsSeq;   // newsSeq는 mysql에서의 pk

    private String newsTitle;
    private ArrayList<String> newsSummary;
    private LocalDateTime newsCreatedAt;

    private String newsReporter;
    private String newsImgUrl;
    private String newsMainText;

    private Media media;
    private String newsUrl;
//    private List<Keyword> newsKeyword;

//    private List<Quiz> newsQuiz;

    public NewsDetailsResponseDto(MetaNews metaNews) {
        this.newsTitle = metaNews.getNewsTitle();
        this.newsSummary = metaNews.getNewsSummary();
        this.newsCreatedAt = LocalDateTime.parse(metaNews.getNewsCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));  // String to LocalDateTime
        this.newsReporter = metaNews.getNewsReporter();
        this.newsImgUrl = metaNews.getNewsImgUrl();
        this.newsMainText = metaNews.getNewsMainText();
        this.newsUrl = metaNews.getNewsUrl();
        this.media = metaNews.getMedia();
    }

}
