package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.dto.MostSummaryRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.OtherRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserRecommendResponseDto;

import java.util.List;

public interface NewsRecommendRepositoryCustom {
    List<UserRecommendResponseDto> findNewsRecommendations(int userSeq);
    List<OtherRecommendResponseDto> findOtherNewsRecommendations(int userSeq);
    List<MostViewRecommendResponseDto> findMostViewNewsRecommendations();
    List<MostSummaryRecommendResponseDto> findMostSummaryNewsRecommendations();
}
