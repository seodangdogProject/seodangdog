package com.ssafy.seodangdogbe.news.service;

import com.ssafy.seodangdogbe.exception.UnauthorizedException;
import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.news.repository.MypageRecentNewsRepositoryCustom;
import com.ssafy.seodangdogbe.news.repository.NewsRecommendRepositoryCustom;
import com.ssafy.seodangdogbe.news.repository.MypageRecentNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsRecommendService {

    @Autowired
    private FastApiService fastApiService;

    @Autowired
    private NewsRecommendRepositoryCustom newsRecommendRepository;
    @Autowired
    private MypageRecentNewsRepositoryCustom mypageRecentNewsRepository;

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

    public List<RecentSolvedDto> getRecentSolvedNews() {
        try {
            return mypageRecentNewsRepository.findRecentSolvedNews();
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<RecentNotSolvedDto> getRecentNotSolvedNews() {
        try {
            return mypageRecentNewsRepository.findRecentNotSolvedNews();
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }
}
