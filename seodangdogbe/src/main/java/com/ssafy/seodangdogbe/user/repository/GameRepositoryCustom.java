package com.ssafy.seodangdogbe.user.repository;

import com.ssafy.seodangdogbe.user.domain.UserWord;
import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;

import java.util.List;

public interface GameRepositoryCustom {
    long countUserWords(int userSeq);
    List<UserWord> findRandomWordsByUserSeq(int userSeq, int limit);
    void deleteWordsBySeqsAndUserSeq(List<Long> wordSeqs, int userSeq);

}
