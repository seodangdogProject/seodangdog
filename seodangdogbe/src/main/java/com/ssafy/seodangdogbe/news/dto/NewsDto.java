package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.news.domain.News;
import lombok.*;

import java.time.LocalDateTime;

public class NewsDto {
    
    // mysql 뉴스 테이블 접근
    @Getter
    @NoArgsConstructor
    public static class NewsResponseDto {
        private Long newsSeq;
        private String mediaCode;

        private String newsAccessId;

        private int countSolve;
        private int countView;

        private String newsImgUrl;
        private String newsTitle;
        private String newsDescription;
        private LocalDateTime newsCreatedAt;    // 뉴스 published Date

        // ** news url은 필요 없는지?

        public NewsResponseDto(News news){
            this.newsSeq = news.getNewsSeq();
            this.mediaCode = news.getMedia().getMediaCode();
            this.newsAccessId = news.getNewsAccessId();
            this.countSolve = news.getCountSolve();
            this.countView = news.getCountView();
            this.newsImgUrl = news.getNewsImgUrl();
            this.newsTitle = news.getNewsTitle();
            this.newsDescription = news.getNewsDescription();
            this.newsCreatedAt = news.getNewsCreatedAt();

        }
    }



}
