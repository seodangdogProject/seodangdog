package com.ssafy.seodangdogbe.keyword.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
<<<<<<< HEAD
import com.ssafy.seodangdogbe.keyword.dto.JoinKeywordDto;
=======
import com.ssafy.seodangdogbe.common.MessageResponseDto;
>>>>>>> 20d6739a7a8646ef8a7fde4e7fbcb10f7bb59cea
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.keyword.service.KeywordService;
import com.ssafy.seodangdogbe.news.service.NewsService;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
=======
import org.springframework.http.HttpStatus;
>>>>>>> 20d6739a7a8646ef8a7fde4e7fbcb10f7bb59cea
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

    @PostMapping("/refre")
    public ResponseEntity<MessageResponseDto> minusWeight(@RequestBody List<NewsRefreshReqDto> newsRefreshReqDtoList){
        User user = userService.getUser();
        MessageResponseDto result = keywordService.minusKeywordListWeight(user, newsRefreshReqDtoList, -0.35, -0.2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/join/keyword")
    public ResponseEntity<List<JoinKeywordDto>> getAllKeywords() {
        return ResponseEntity.ok(keywordService.findAllKeywords());
    }

//    @GetMapping("/join-keyword")
//    public List<String> getAllKeywords(){
//        User user = userService.getUser();
//        List<Join>
//    }

}
