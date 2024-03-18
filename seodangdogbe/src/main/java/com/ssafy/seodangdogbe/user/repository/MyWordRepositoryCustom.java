package com.ssafy.seodangdogbe.user.repository;

import com.ssafy.seodangdogbe.user.domain.UserWord;
import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;

import java.util.List;

public interface MyWordRepositoryCustom {

    List<UserWord> findAllUserWords(int userSeq);


}
