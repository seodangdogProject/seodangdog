package com.ssafy.seodangdogbe.news.service;

import com.ssafy.seodangdogbe.auth.service.UserService;
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

    @Autowired
    private UserService userService;

    public List<UserRecommendResponseDto> getNewsRecommendations() {
        try {
            int userSeq = userService.getUserSeq();
            return newsRecommendRepository.findNewsRecommendations(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<MostViewRecommendResponseDto> getMostViewNewsRecommendations() {
        try {
            int userSeq = userService.getUserSeq();
            return newsRecommendRepository.findMostViewNewsRecommendations(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<MostSummaryRecommendResponseDto> getMostSummaryNewsRecommendations() {
        try {
            int userSeq = userService.getUserSeq();
            return newsRecommendRepository.findMostSummaryNewsRecommendations(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<OtherRecommendResponseDto> getOtherNewsRecommendations() {
        try {
            int userSeq = userService.getUserSeq();
            return newsRecommendRepository.findOtherNewsRecommendations(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<RecentSolvedDto> getRecentSolvedNews() {
        try {
            int userSeq = userService.getUserSeq();
            return mypageRecentNewsRepository.findRecentSolvedNews(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }

    public List<RecentNotSolvedDto> getRecentNotSolvedNews() {
        try {
            int userSeq = userService.getUserSeq();
            return mypageRecentNewsRepository.findRecentNotSolvedNews(userSeq);
        } catch (Exception e) {
            throw new UnauthorizedException("미인증 사용자입니다.");
        }
    }
}
