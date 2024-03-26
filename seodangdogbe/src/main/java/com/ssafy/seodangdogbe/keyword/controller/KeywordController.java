package com.ssafy.seodangdogbe.keyword.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.keyword.service.KeywordService;
import com.ssafy.seodangdogbe.news.service.NewsService;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KeywordController {

    public final UserService userService;
    public final NewsService newsService;
    public final KeywordService keywordService;

    @PostMapping("/keyword") // controller 어떻게 맞추기로 했는 지모루겠어서 둘게욤 ~
    public void addWeight(@RequestBody List<String> keywords){
        int userSeq = userService.getUserSeq();
        User user = userService.getUserByUserSeq(userSeq);
        keywordService.addKeywordListWeight(user, keywords, 1.28);
    }

    @PostMapping("/keyword/lose")
    public void minusWeight(@RequestBody List<NewsRefreshReqDto> newsRefreshReqDtoList){
        int userSeq = userService.getUserSeq();
        User user = userService.getUserByUserSeq(userSeq);
        keywordService.minusKeywordListWeight(user, newsRefreshReqDtoList, -0.35, -0.2);
    }

}
