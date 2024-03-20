package com.ssafy.seodangdogbe.news.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserRecommendResponseDto {

    private List<NewsPreviewList> newsPreviewList;

    @Data
    @Builder
    @AllArgsConstructor
    public static class NewsPreviewList {
        private Long newsSeq;
        private String newsImgUrl;
        private String newsTitle;
        private String newsDescription;
        private LocalDateTime newsCreatedAt;
        private List<String> newsKeyword;
    }

}
