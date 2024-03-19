package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.news.dto.MostSummaryRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.OtherRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserRecommendResponseDto;
import com.ssafy.seodangdogbe.news.service.NewsRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main")
public class NewsRecommendController {

    private final NewsRecommendService newsRecommendService;

    @Autowired
    public NewsRecommendController(NewsRecommendService newsRecommendService) {
        this.newsRecommendService = newsRecommendService;
    }

    @GetMapping("/user-recommend")
    public List<UserRecommendResponseDto> getNewsRecommendations(int userSeq) {
        return newsRecommendService.getNewsRecommendations(userSeq);
    }

    @GetMapping("/most-view")
    public List<MostViewRecommendResponseDto> getMostViewNewsRecommendations(int userSeq) {
        return newsRecommendService.getMostViewNewsRecommendations(userSeq);
    }

    @GetMapping("/most-summary")
    public List<MostSummaryRecommendResponseDto> getMostSummaryNewsRecommendations(int userSeq) {
        return newsRecommendService.getMostSummaryNewsRecommendations(userSeq);
    }

    @GetMapping("/other-recommend")
    public List<OtherRecommendResponseDto> getOtherNewsRecommendations(int userSeq) {
        return newsRecommendService.getOtherNewsRecommendations(userSeq);
    }
}
