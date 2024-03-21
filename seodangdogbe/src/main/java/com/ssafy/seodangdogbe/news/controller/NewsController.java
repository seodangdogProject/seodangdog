package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;
import com.ssafy.seodangdogbe.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
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
    public final UserService userService;

    // newsSeq로 news 본문 조회
    @Operation(description = "newsSeq(mysql pk)로 mongodb에 있는 뉴스 본문 조회")
    @GetMapping("/{newsSeq}")
    public NewsDetailsResponseDto getNewsDetails(@PathVariable(name = "newsSeq") Long newsSeq){
        // ** 로그인한 사용자 jwt에서 userSeq를 가져오기
        int userSeq = userService.getUserSeq();

        NewsDetailsResponseDto newsDetailsResponseDto = newsService.getNewsDetailsByNewsSeq(newsSeq);

        if (!newsService.getUserNewsExist(userSeq, newsSeq)){    // 사용자-뉴스 테이블에 기록이 없을 경우(최초접근 데이터 넣기)
            newsService.setUserNewsInit(userSeq, newsSeq);

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
    @Operation(description = "사용자 읽기기록 저장")
    @PatchMapping("/read")
    public ResponseEntity<MsgResponseDto> setReadRecord(@RequestBody UserNewsReadRequestDto dto){
        // ** 로그인한 사용자 jwt에서 userSeq를 가져오기
        int userSeq = userService.getUserSeq();

        if (newsService.setUserNewsRead(userSeq, dto))
            return ResponseEntity.status(HttpStatus.OK).body(new MsgResponseDto("읽기기록 저장 성공"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MsgResponseDto("읽기기록 저장 오류"));
    }

    // 사용자 풀이기록 저장
    @Operation(description = "사용자 풀이기록 저장")
    @PatchMapping("/solve")
    public ResponseEntity<MsgResponseDto> setSolveRecord(@RequestBody UserNewsSolveRequestDto dto){
        // ** 로그인한 사용자 jwt에서 userSeq를 가져오기
        int userSeq = userService.getUserSeq();

        if (newsService.setUserNewsSolve(userSeq, dto))
            return ResponseEntity.status(HttpStatus.OK).body(new MsgResponseDto("풀이기록 저장 성공"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MsgResponseDto("풀이기록 저장 실패"));
    }



}
