package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.news.dto.MostSummaryRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.RecentNotSolvedDto;
import com.ssafy.seodangdogbe.news.dto.RecentSolvedDto;
import com.ssafy.seodangdogbe.news.service.NewsRecommendService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypages")
public class MypageRecentNewsController {
    private final NewsRecommendService newsRecommendService;

    @Autowired
    public MypageRecentNewsController(NewsRecommendService newsRecommendService) {
        this.newsRecommendService = newsRecommendService;
    }

    @GetMapping("/recent_seen")
    @Operation(description = "마이페이지 - 최근 본 뉴스 조회")
    public List<RecentNotSolvedDto> getRecentNotSolvedNews() {
        return newsRecommendService.getRecentNotSolvedNews();
    }

    @GetMapping("/recent_solved")
    @Operation(description = "마이페이지 - 최근 푼 뉴스 조회")
    public List<RecentSolvedDto> getRecentSolvedNews() {
        return newsRecommendService.getRecentSolvedNews();
    }

}
