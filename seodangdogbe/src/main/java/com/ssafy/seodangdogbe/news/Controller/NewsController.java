package com.ssafy.seodangdogbe.news.Controller;

import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.news.Servcie.NewsService;
import com.ssafy.seodangdogbe.news.dto.NewsDetailsResponseDto;
import com.ssafy.seodangdogbe.news.dto.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;

    // newsSeq로 news 본문 조회
    @GetMapping("/{newsSeq}")
    public NewsDetailsResponseDto getNewsDetails(@PathVariable Long newsSeq){
        // newsSeq(Long)로 mysql news 테이블에서 newsAccessId(String)를 가져온다.
        NewsResponseDto newsResponseDto = newsService.getNewsPreview(newsSeq);
        String newsAccessId = newsResponseDto.getNewsAccessId();
        System.out.println("newsSeq : "+newsSeq+" / newsAccessId : "+newsAccessId);

        // newsAccessId(String)로 mongodb의 news collection에서 뉴스 원본을 가져온다.
        NewsDetailsResponseDto newsDetailsResponseDto = newsService.getNewsDetails(newsAccessId);
        System.out.println(newsAccessId);
        return newsDetailsResponseDto;
    }

    @GetMapping("/all-news")
    public void getAllNewsDetails(){
        List<NewsDetailsResponseDto> list= newsService.getNewsDetailsList();
        System.out.println("count NewsDetails : " + list.size());
    }

}
