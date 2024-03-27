package com.ssafy.seodangdogbe.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MFRecommendResponse {
    int userSeq;
    Long newsSeq;
    double weight;
}
