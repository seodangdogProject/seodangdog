package com.ssafy.seodangdogbe.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Getter
@Setter
public class MainNewsPreviewDto {

    private Long newsSeq;
    private String newsImgUrl;
    private String newsTitle;
    private String newsDescription;
    private LocalDateTime newsCreatedAt;
    private int countView;
    private int countSolve;
    private String media;
    private Map<String, Double> newsKeyword;

}
