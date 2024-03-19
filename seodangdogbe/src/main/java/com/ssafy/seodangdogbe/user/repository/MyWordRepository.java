package com.ssafy.seodangdogbe.user.repository;

import com.ssafy.seodangdogbe.user.domain.UserWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyWordRepository extends JpaRepository<UserWord, Long>, MyWordRepositoryCustom{
}
