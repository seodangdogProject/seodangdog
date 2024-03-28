package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.dto.MostSummaryRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.OtherRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserRecommendResponseDto;
import com.ssafy.seodangdogbe.user.domain.User;

import java.util.List;

public interface NewsRecommendRepositoryCustom {
    List<UserRecommendResponseDto> findNewsRecommendations(User user);
    List<OtherRecommendResponseDto> findOtherNewsRecommendations(int userSeq);
    List<MostViewRecommendResponseDto> findMostViewNewsRecommendations(int userSeq);
    List<MostSummaryRecommendResponseDto> findMostSummaryNewsRecommendations(int userSeq);
}
