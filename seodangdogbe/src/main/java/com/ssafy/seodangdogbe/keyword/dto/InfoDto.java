package com.ssafy.seodangdogbe.keyword.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class InfoDto {
    @JsonProperty("news_seq")
    Long newsSeq;
    @JsonProperty("weight")
    double weight;
}
