package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.OtherRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.RecentNotSolvedDto;
import com.ssafy.seodangdogbe.news.dto.RecentSolvedDto;

import java.util.List;

public interface MypageRecentNewsRepositoryCustom {
    List<RecentSolvedDto> findRecentSolvedNews();
    List<RecentNotSolvedDto> findRecentNotSolvedNews();
}
