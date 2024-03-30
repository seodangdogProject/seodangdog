package com.ssafy.seodangdogbe.keyword.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loseWeightFastReqDto {
    @JsonProperty("user_seq")
    int userSeq;
    List<InfoDto> info;
}
