package com.ssafy.seodangdogbe.keyword.dto;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class NewsRefreshReqDto { // 사용자는 안본 뉴스의 정보만 보내준다

    Long newSeq;
    String[] keyword;
}


