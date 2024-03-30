package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.news.service.NewsRecommendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main")
@Slf4j
public class NewsRecommendController {

    private final NewsRecommendService newsRecommendService;
    private final UserService userService;

    @Autowired
    public NewsRecommendController(NewsRecommendService newsRecommendService, UserService userService) {
        this.newsRecommendService = newsRecommendService;
        this.userService = userService;
    }

//    @GetMapping("/user-recommend")
//    @Operation(description = "메인페이지 - 맞춤형 뉴스 추천")
//    public List<UserRecommendResponseDto> getNewsRecommendations() {
//        log.info(" **** 맞춤 뉴스 추천 *** ");
//        return newsRecommendService.getNewsRecommendations();
//    }

    @GetMapping("/user-recommend/v2")
    @Operation(description = "메인페이지 - 맞춤형 뉴스 추천")
    public UserRecommendResponseDtoV2 getNewsRecommendationsV2() {
        log.info(" **** 맞춤 뉴스 추천 *** ");
        return newsRecommendService.getNewsRecommendationsV2();
    }

    @GetMapping("/other-recommend")
    @Operation(description = "메인페이지 - 맞춤형 뉴스 추천")
    public List<OtherRecommendResponseDto> getOtherNewsRecommendations() {
        return newsRecommendService.getOtherNewsRecommendations();
    }

    @GetMapping("/most-view")
    @Operation(description = "메인페이지 - 많이 본 뉴스 조회")
    public List<MostViewRecommendResponseDto> getMostViewNewsRecommendations() {
        return newsRecommendService.getMostViewNewsRecommendations();
    }

    @GetMapping("/most-solved")
    @Operation(description = "메인페이지 - 많이 푼 뉴스 조회")
    public List<MostSummaryRecommendResponseDto> getMostSummaryNewsRecommendations() {
        return newsRecommendService.getMostSummaryNewsRecommendations();
    }
}
