package com.ssafy.seodangdogbe.keyword.repository;

import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {

    List<UserKeyword> findAllByUserUserSeq(int userSeq);
}
