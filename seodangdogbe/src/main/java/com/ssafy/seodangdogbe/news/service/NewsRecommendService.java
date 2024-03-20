package com.ssafy.seodangdogbe.news.service;

import com.ssafy.seodangdogbe.exceptions.UnauthorizedException;
import com.ssafy.seodangdogbe.news.dto.MostSummaryRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.OtherRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserRecommendResponseDto;
import com.ssafy.seodangdogbe.news.repository.NewsRecommendRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsRecommendService {

    private final NewsRecommendRepositoryCustom newsRecommendRepository;

    public List<UserRecommendResponseDto> getNewsRecommendations(int userSeq) {
        try {
            return newsRecommendRepository.findNewsRecommendations(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<MostViewRecommendResponseDto> getMostViewNewsRecommendations() {
        try {
            return newsRecommendRepository.findMostViewNewsRecommendations();
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<MostSummaryRecommendResponseDto> getMostSummaryNewsRecommendations() {
        try {
            return newsRecommendRepository.findMostSummaryNewsRecommendations();
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<OtherRecommendResponseDto> getOtherNewsRecommendations(int userSeq) {
        try {
            return newsRecommendRepository.findOtherNewsRecommendations(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

}
