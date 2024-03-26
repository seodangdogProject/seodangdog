package com.ssafy.seodangdogbe.news.dto;

import com.ssafy.seodangdogbe.news.domain.News;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class NewsPreviewListDto {
    private Long newsSeq;
    private String newsImgUrl;
    private String newsTitle;
    private String newsDescription;
    private LocalDateTime newsCreatedAt;
    private List<String> newsKeyword;

    // News를 가져와서 미리보기 Dto로 변환
    public NewsPreviewListDto(News news) {
        this.newsSeq = news.getNewsSeq();
        this.newsImgUrl = news.getNewsImgUrl();
        this.newsTitle = news.getNewsTitle();
        this.newsDescription = news.getNewsDescription();
        this.newsCreatedAt = news.getNewsCreatedAt();
        this.newsKeyword = news.getKeywordNewsList().stream()
                .map(keywordNews -> keywordNews.getKeyword().getKeyword())
                .collect(Collectors.toList());
//        List<String> keywords = news.getKeywordNewsList().stream()
//                .map(keywordNews -> keywordNews.getKeyword().getKeyword())
//                .collect(Collectors.toList());

    }
}
