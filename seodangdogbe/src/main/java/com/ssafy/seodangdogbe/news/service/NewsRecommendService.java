package com.ssafy.seodangdogbe.news.service;

import com.ssafy.seodangdogbe.exceptions.UnauthorizedException;
import com.ssafy.seodangdogbe.news.dto.MostSummaryRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.OtherRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserRecommendResponseDto;
import com.ssafy.seodangdogbe.news.repository.NewsRecommendRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.ssafy.seodangdogbe.news.service.FastApiService.CbfRecommendResponse;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsRecommendService {

    @Autowired
    private FastApiService fastApiService;

    @Autowired
    private NewsRecommendRepositoryCustom newsRecommendRepository;

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

    public List<OtherRecommendResponseDto> getOtherNewsRecommendations() {
        try {
            return newsRecommendRepository.findOtherNewsRecommendations();
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }
}
