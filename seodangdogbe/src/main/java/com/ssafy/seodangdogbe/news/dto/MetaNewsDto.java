package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.media.domain.Media;
import com.ssafy.seodangdogbe.news.domain.MetaNews;
import com.ssafy.seodangdogbe.news.domain.NewsPos;
import com.ssafy.seodangdogbe.news.domain.UserSummary;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

// mongodb MetaNews 관련 dto
public class MetaNewsDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class MetaNewsResponseDto {
//        private Long newsSeq;   // newsSeq는 mysql에서의 pk

        private String newsTitle;
        private List<String> newsSummary;
        private LocalDateTime newsCreatedAt;

        private String newsReporter;
        private String newsImgUrl;
        private String newsMainText;

        private Media media;
        private String newsUrl;

        private List<NewsPos> newsPos;
        private Map<String, Double> newsKeyword;
        private Map<String, Double> newsSummaryKeyword;

    //    private List<Quiz> newsQuiz;

        // 뉴스 풀이 기록
        @Setter
        private List<Integer> highlightList;
        @Setter
        private List<Integer> wordList;
        @Setter
        private List<Integer> userAnswerList;
        @Setter
        private UserSummary userSummary;

        public MetaNewsResponseDto(MetaNews metaNews) {
            this.newsTitle = metaNews.getNewsTitle();
            this.newsSummary = metaNews.getNewsSummary();
            this.newsCreatedAt = LocalDateTime.parse(metaNews.getNewsCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));  // String to LocalDateTime
            this.newsReporter = metaNews.getNewsReporter();
            this.newsImgUrl = metaNews.getNewsImgUrl();
            this.newsMainText = metaNews.getNewsMainText();
            this.newsUrl = metaNews.getNewsUrl();
            this.media = metaNews.getMedia();
            this.newsPos = metaNews.getNewsPos();
            this.newsKeyword = metaNews.getNewsKeyword();
            this.newsSummaryKeyword = metaNews.getNewsSummaryKeyword();
        }

    }

}
