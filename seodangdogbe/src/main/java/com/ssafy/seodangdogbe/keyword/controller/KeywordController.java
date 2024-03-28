package com.ssafy.seodangdogbe.keyword.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.keyword.service.KeywordService;
import com.ssafy.seodangdogbe.news.service.NewsService;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KeywordController {

    public final UserService userService;
    public final NewsService newsService;
    public final KeywordService keywordService;

    @PostMapping("/keyword/click")
    public void addWeight(@RequestBody List<String> keywords){
        int userSeq = userService.getUserSeq();
        User user = userService.getUser();
        keywordService.addKeywordListWeight(user, keywords, 1.28);
    }

    @PostMapping("/keyword/refre")
    public ResponseEntity<MessageResponseDto> minusWeight(@RequestBody List<NewsRefreshReqDto> newsRefreshReqDtoList){
        int userSeq = userService.getUserSeq();
        User user = userService.getUser();
        MessageResponseDto result = keywordService.minusKeywordListWeight(user, newsRefreshReqDtoList, -0.35, -0.2);
        return ResponseEntity.ok(result);
    }

}
