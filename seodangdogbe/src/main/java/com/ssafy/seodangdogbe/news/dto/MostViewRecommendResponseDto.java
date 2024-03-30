package com.ssafy.seodangdogbe.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MostViewRecommendResponseDto {
    private List<NewsPreviewListDto> newsPreviewList;

}
