package com.ssafy.seodangdogbe.word.repository;

import com.ssafy.seodangdogbe.word.domain.UserWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWordRepository extends JpaRepository<UserWord, Long> {
    Optional<UserWord> findByUserUserSeqAndWord(int userSeq, String word);



}
