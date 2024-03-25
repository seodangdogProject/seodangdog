package com.ssafy.seodangdogbe.keyword.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
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

    @GetMapping("/keyword")
    public void getKeywords(@RequestBody List<String> keywords){
        int userSeq = userService.getUserSeq();
        User user = userService.getUserByUserSeq(userSeq);

//        List<Keyword> keywords = new ArrayList<>();
//        List<String> keywords = new ArrayList<>();

        keywordService.addKeywordListWeight(user, keywords, 1.28);
    }

}
