package com.ssafy.seodangdogbe.word.repository;

import com.ssafy.seodangdogbe.word.domain.UserWord;

import java.util.List;

public interface MyWordRepositoryCustom {

    List<UserWord> findAllUserWords(int userSeq);


}
