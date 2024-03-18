package com.ssafy.seodangdogbe.user.repository;

import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;

import java.util.List;

public interface MyWordRepositoryCustom {

    List<MyWordResponseDto> findAllUserWords(int userSeq);

}
