package com.ssafy.seodangdogbe.news.Controller;

import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.news.Servcie.NewsService;
import com.ssafy.seodangdogbe.news.dto.NewsDetailsResponseDto;
import com.ssafy.seodangdogbe.news.dto.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;

    // newsSeq로 news 본문 조회
    @GetMapping("/{newsSeq}")
    public NewsDetailsResponseDto getNewsDetails(@PathVariable Long newsSeq){
//        // newsSeq(Long)로 mysql news 테이블에서 newsAccessId(String)를 가져온다.
//        NewsResponseDto newsResponseDto = newsService.getNewsPreview(newsSeq);
//        String newsAccessId = newsResponseDto.getNewsAccessId();
//        System.out.println("news Access Id : "+newsAccessId);

        // newsAccessId(String)로 mongodb의 news collection에서 뉴스 원본을 가져온다.
        String newsAccessId = "65f7a3cfc11b58ae8656a5aa";
        NewsDetailsResponseDto newsDetailsResponseDto = newsService.getNewsDetails(newsAccessId);
        System.out.println(newsAccessId);
        return newsDetailsResponseDto;
    }

    @PostMapping("/insert")
    public MsgResponseDto insertNewsInMongo(){
        // mongodb에서 뉴스 컬렉션에서 metanews 읽어오기

        // mysql에 저장할 형식으로 변환(metanews -> news)
        // mysql에 저장

        return new MsgResponseDto("fail");
    }
}
