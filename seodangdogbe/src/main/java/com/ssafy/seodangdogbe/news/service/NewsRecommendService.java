package com.ssafy.seodangdogbe.news.service;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.exception.UnauthorizedException;
import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.news.repository.MypageRecentNewsRepositoryCustom;
import com.ssafy.seodangdogbe.news.repository.NewsRecommendRepositoryCustom;
import com.ssafy.seodangdogbe.news.repository.MypageRecentNewsRepository;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
        User user = userService.getUser();
        return newsRecommendRepository.findNewsRecommendations(user);
    }

    public List<MostViewRecommendResponseDto> getMostViewNewsRecommendations() {
            int userSeq = userService.getUserSeq();
            return newsRecommendRepository.findMostViewNewsRecommendations(userSeq);
    }

    public List<MostSummaryRecommendResponseDto> getMostSummaryNewsRecommendations() {
            int userSeq = userService.getUserSeq();
            return newsRecommendRepository.findMostSummaryNewsRecommendations(userSeq);
    }

    public List<OtherRecommendResponseDto> getOtherNewsRecommendations() {
            int userSeq = userService.getUserSeq();
            return newsRecommendRepository.findOtherNewsRecommendations(userSeq);
    }

    public List<RecentSolvedDto> getRecentSolvedNews() {
            int userSeq = userService.getUserSeq();
            return mypageRecentNewsRepository.findRecentSolvedNews(userSeq);
    }

    public List<RecentNotSolvedDto> getRecentNotSolvedNews() {
            int userSeq = userService.getUserSeq();
            return mypageRecentNewsRepository.findRecentNotSolvedNews(userSeq);
    }
}
