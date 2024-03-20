package com.ssafy.seodangdogbe.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MostSummaryRecommendResponseDto {
    private List<NewsPreviewListDto> newsPreviewList;

}
