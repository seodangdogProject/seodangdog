package com.ssafy.seodangdogbe.keyword.repository;

import com.ssafy.seodangdogbe.keyword.domain.JoinKeyword;
import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinKeywordRepository extends JpaRepository<JoinKeyword, Long>, JoinKeywordRepositoryCustom{
}
