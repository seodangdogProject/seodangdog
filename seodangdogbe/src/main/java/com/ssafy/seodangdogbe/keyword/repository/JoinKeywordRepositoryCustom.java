package com.ssafy.seodangdogbe.keyword.repository;

import com.ssafy.seodangdogbe.keyword.domain.JoinKeyword;

import java.util.List;

public interface JoinKeywordRepositoryCustom {
    List<JoinKeyword> findAllKeywords();
}
