package com.ssafy.seodangdogbe.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OtherRecommendResponseDto {
    private List<NewsPreviewListDto> newsPreviewList;
}
