package com.ssafy.seodangdogbe.news.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class UserRecommendResponseDto {

    private List<NewsPreviewList> newsPreviewList;

    @Data
    @AllArgsConstructor
    public static class NewsPreviewList {
        private Long newsSeq;
        private String newsImgUrl;
        private String newsTitle;
        private String newsDescription;
        private String newsCreatedAt;
        private List<String> newsKeyword;
    }

}
