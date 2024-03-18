package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.media.domain.Media;
import com.ssafy.seodangdogbe.news.domain.KeywordNews;
import com.ssafy.seodangdogbe.news.domain.News;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class NewsResponseDto {
    private Long newsSeq;
    private String mediaCode;

    private String newsAccessId;

    private int countSolve;
    private int countView;

    private String newsImgUrl;
    private String newsTitle;
    private String newsDescription;
    private LocalDateTime newsCreatedAt;

    @Builder
    public NewsResponseDto(News news) {
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
