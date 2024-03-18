package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;

import java.util.List;

public interface MyWordService {
    List<MyWordResponseDto> findAllUserWords(int userSeq);
}
