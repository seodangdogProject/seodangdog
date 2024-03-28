package com.ssafy.seodangdogbe.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loseWeightFastReqDto {
    int userSeq;
    List<InfoDto> info;
}
