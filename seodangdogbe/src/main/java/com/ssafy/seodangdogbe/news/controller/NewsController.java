package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;
import com.ssafy.seodangdogbe.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.seodangdogbe.news.dto.NewsDetailsDto.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;

    // newsSeq로 news 본문 조회
    @GetMapping("/{newsSeq}")
    public NewsDetailsResponseDto getNewsDetails(@PathVariable(name = "newsSeq") Long newsSeq){
        // ** 로그인한 사용자 jwt에서 userSeq를 가져오기
        int userSeq = 1;

//        // newsSeq(MySql) 뉴스 조회
//        NewsResponseDto newsResponseDto = newsService.getNewsPreview(newsSeq);
//        String newsAccessId = newsResponseDto.getNewsAccessId();
//        // newsAccessId(MongoDb) 뉴스 조회
//        NewsDetailsResponseDto newsDetailsResponseDto = newsService.getNewsDetails(newsAccessId);

        NewsDetailsResponseDto newsDetailsResponseDto = newsService.getNewsDetailsByNewsSeq(newsSeq);

        if (!newsService.getUserNewsExist(userSeq, newsSeq)){    // 사용자-뉴스 테이블에 기록이 없을 경우(최초접근 데이터 넣기)
            newsService.setUserNewsInit(userSeq, newsSeq);
            // ** 해당 사용자의 본 뉴스 경험치 증가
        } else {    // 사용자-뉴스 접근기록이 있는 경우
            UserNewsResponseDto userRecord = newsService.getReadOrSolveRecord(userSeq, newsSeq);
            newsDetailsResponseDto.setHighlightList(userRecord.getHighlightList());
            newsDetailsResponseDto.setWordList(userRecord.getWordList());
            newsDetailsResponseDto.setUserAnswerList(userRecord.getUserAnswers());
            newsDetailsResponseDto.setUserSummary(userRecord.getUserSummary());
        }

        return newsDetailsResponseDto;
    }

    // 사용자 읽기기록 저장
    @PatchMapping("/read")
    public ResponseEntity<MsgResponseDto> setReadRecord(@RequestBody UserNewsReadRequestDto dto){
        // 사용자 시퀀스 가져오기
        int userSeq = 1;

        if (newsService.setUserNewsRead(userSeq, dto))
            return ResponseEntity.status(HttpStatus.OK).body(new MsgResponseDto("읽기기록 저장 성공"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MsgResponseDto("읽기기록 저장 오류"));
    }

    // 사용자 풀이기록 저장
    @PatchMapping("/solve")
    public ResponseEntity<MsgResponseDto> setSolveRecord(@RequestBody UserNewsSolveRequestDto dto){
        // 사용자 읽기기록 저장과 마찬가지
        int userSeq = 1;

        if (newsService.setUserNewsSolve(userSeq, dto))
            return ResponseEntity.status(HttpStatus.OK).body(new MsgResponseDto("풀이기록 저장 성공"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MsgResponseDto("풀이기록 저장 실패"));
    }



}
