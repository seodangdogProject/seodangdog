package com.ssafy.seodangdogbe.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NewsPreviewListDto {
    private Long newsSeq;
    private String newsImgUrl;
    private String newsTitle;
    private String newsDescription;
    private LocalDateTime newsCreatedAt;
    private List<String> newsKeyword;
}
