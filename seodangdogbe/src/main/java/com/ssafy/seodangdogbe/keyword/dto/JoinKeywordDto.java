package com.ssafy.seodangdogbe.keyword.dto;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinKeywordDto {
    private int joinKeywordSeq;
    private String keyword;

}
