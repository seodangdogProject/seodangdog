package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.user.domain.User;

import java.util.List;

public interface NewsRecommendRepositoryCustom {
    List<UserRecommendResponseDto> findNewsRecommendations(User user);
    UserRecommendResponseDtoV2 findNewsRecommendationsV2(User user);

    List<OtherRecommendResponseDto> findOtherNewsRecommendations(int userSeq);
    List<MostViewRecommendResponseDto> findMostViewNewsRecommendations(int userSeq);
    List<MostSummaryRecommendResponseDto> findMostSummaryNewsRecommendations(int userSeq);
}
