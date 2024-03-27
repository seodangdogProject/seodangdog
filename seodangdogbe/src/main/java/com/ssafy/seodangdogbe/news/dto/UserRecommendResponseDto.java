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
    private List<NewsPreviewListDto> newsPreviewList;

}
