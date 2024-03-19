package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.news.dto.NewsDto.NewsResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;
import com.ssafy.seodangdogbe.news.repository.UserNewsRepository;
import com.ssafy.seodangdogbe.news.service.NewsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssafy.seodangdogbe.news.dto.NewsDetailsDto.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;

    // newsSeq로 news 본문 조회
    @GetMapping("/{newsSeq}")
    public NewsDetailsResponseDto getNewsDetails(@PathVariable(name = "newsSeq") Long newsSeq){
        // 로그인한 사용자 jwt에서 userSeq를 가져오자
        int userSeq = 1;

        // newsSeq(Long)로 mysql news 테이블에서 newsAccessId(String)를 가져온다.
        NewsResponseDto newsResponseDto = newsService.getNewsPreview(newsSeq);
        String newsAccessId = newsResponseDto.getNewsAccessId();
        System.out.println("newsSeq : "+newsSeq+" / newsAccessId : "+newsAccessId);

        // newsAccessId(String)로 mongodb의 news 컬렉션에서 뉴스 메타데이터를 가져온다.
        NewsDetailsResponseDto newsDetailsResponseDto = newsService.getNewsDetails(newsAccessId);

        // 사용자뉴스 테이블에 (사용자시퀀스+뉴스시퀀스)로 검색했을 때 없다면 최초접근 -> 사용자뉴스 테이블에 넣기
        if (!newsService.getUserNews(userSeq, newsSeq)){    // 사용자-뉴스 테이블에 없을 경우
            newsService.setUserNewsInit(userSeq, newsSeq);
        }

        return newsDetailsResponseDto;
    }

    @GetMapping("/all-news")
    public void getAllNewsDetails(){
        List<NewsDetailsResponseDto> list= newsService.getNewsDetailsList();
        System.out.println("count NewsDetails : " + list.size());
    }

    // 사용자 읽기기록 저장
    @PatchMapping("/read")
    public ResponseEntity<MsgResponseDto> setReadRecord(@RequestBody UserNewsReadRequestDto dto){
        // 사용자 시퀀스 가져오기
        // 미인증 사용자의 경우 걸러주기 401: "미인증 사용자입니다." <- 꼭 있어야하는지
        MsgResponseDto msgResponseDto = newsService.setUserNewsRead(1, dto);
        return ResponseEntity.status(HttpStatus.OK).body(msgResponseDto);
    }

    // 사용자 풀이기록 저장
    @PatchMapping("/solve")
    public ResponseEntity<MsgResponseDto> setSolveRecord(@RequestBody UserNewsSolveRequestDto dto){
        // 사용자 읽기기록 저장과 마찬가지
        MsgResponseDto msgResponseDto = newsService.setUserNewsSolve(1, dto);
        return ResponseEntity.status(HttpStatus.OK).body(msgResponseDto);
    }


}
