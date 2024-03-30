package com.ssafy.seodangdogbe.keyword.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.keyword.dto.DeWeightReqDto;
import com.ssafy.seodangdogbe.keyword.dto.JoinKeywordDto;
import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.domain.JoinKeyword;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.keyword.service.KeywordService;
import com.ssafy.seodangdogbe.news.service.NewsService;
import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keyword")
@RequiredArgsConstructor
public class KeywordController {

    public final UserService userService;
    public final NewsService newsService;
    public final KeywordService keywordService;


    @PostMapping("/click")
    public void addWeight(@RequestBody List<String> keywords){
        User user = userService.getUser();
        keywordService.addKeywordListWeight(user, keywords, 1.28);
    }

//    @PostMapping("/refre")
//    public ResponseEntity<MessageResponseDto> minusWeight(@RequestBody List<NewsRefreshReqDto> newsRefreshReqDtoList){
//        User user = userService.getUser();
//        MessageResponseDto result = keywordService.minusKeywordListWeight(user, newsRefreshReqDtoList, -0.35, -0.2);
//        return ResponseEntity.ok(result);
//    }

    @Operation(description = "새로고침시 가중치 내리기")
    @PostMapping("/refre")
    public ResponseEntity<MessageResponseDto> minusWeightV2(@RequestBody List<DeWeightReqDto> deWeightReqDtosList){
        User user = userService.getUser();
        MessageResponseDto result = keywordService.minusKeywordListWeightV2(user, deWeightReqDtosList);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/join")
    public ResponseEntity<List<JoinKeywordDto>> getAllKeywords() {
        return ResponseEntity.ok(keywordService.findAllKeywords());
    }


}
