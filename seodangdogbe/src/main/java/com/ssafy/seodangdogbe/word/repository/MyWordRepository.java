package com.ssafy.seodangdogbe.word.repository;

import com.ssafy.seodangdogbe.word.domain.UserWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyWordRepository extends JpaRepository<UserWord, Long>, MyWordRepositoryCustom{
}
