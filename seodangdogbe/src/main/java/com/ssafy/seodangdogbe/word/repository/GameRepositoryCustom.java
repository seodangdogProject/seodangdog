package com.ssafy.seodangdogbe.word.repository;

import com.ssafy.seodangdogbe.word.domain.UserWord;

import java.util.List;

public interface GameRepositoryCustom {
    long countUserWords(int userSeq);
    List<UserWord> findRandomWordsByUserSeq(int userSeq, int limit);
    void deleteWordsBySeqsAndUserSeq(List<Long> wordSeqs, int userSeq);

}
