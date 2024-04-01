package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageAlterResponseDto;
import com.ssafy.seodangdogbe.keyword.service.KeywordService;
import com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;
import com.ssafy.seodangdogbe.news.service.NewsService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.service.UserBadgeService;
import com.ssafy.seodangdogbe.word.controller.WordController;
import com.ssafy.seodangdogbe.word.dto.WordApiDto;
import com.ssafy.seodangdogbe.word.dto.UserWordDto;
import com.ssafy.seodangdogbe.word.dto.WordDto;
import com.ssafy.seodangdogbe.word.dto.WordDto.WordRequestDto;
import com.ssafy.seodangdogbe.word.service.UserWordService;
import com.ssafy.seodangdogbe.word.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.seodangdogbe.news.dto.MetaNewsDto.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;
    private final KeywordService keywordService;

    private final UserBadgeService userBadgeService;

    @Operation(description = "newsSeq(mysql pk)로 mongodb에 있는 뉴스 본문 조회")
    @GetMapping("/{newsSeq}")
    public MetaNewsResponseDto getNewsDetails(@PathVariable(name = "newsSeq") Long newsSeq){
        // 로그인한 사용자 jwt에서 user가져오기
       User user = userService.getUser();

        MetaNewsResponseDto metaNewsResponseDto = newsService.getNewsDetailsByNewsSeq(newsSeq);
        List<String> newsKeywordList = new ArrayList<>(metaNewsResponseDto.getNewsKeyword().keySet());

        if (!newsService.getUserNewsExist(user.getUserSeq(), newsSeq)){    // 사용자-뉴스 테이블에 기록이 없을 경우 (최초접근 데이터 넣기)
            keywordService.addKeywordListWeight(user, newsKeywordList, 1.28);
            newsService.setUserNewsInit(user.getUserSeq(), newsSeq);
        } else {    // 사용자-뉴스 접근기록이 있는 경우
            keywordService.addKeywordListWeight(user, newsKeywordList, 0.33);
            UserNewsResponseDto userRecord = newsService.getReadOrSolveRecord(user.getUserSeq(), newsSeq);
            metaNewsResponseDto.setHighlightList(userRecord.getHighlightList());
            metaNewsResponseDto.setWordList(userRecord.getWordList());
            metaNewsResponseDto.setUserAnswerList(userRecord.getUserAnswers());
            metaNewsResponseDto.setUserSummary(userRecord.getUserSummary());
            metaNewsResponseDto.setSolved(userRecord.isSolved());
        }

        return metaNewsResponseDto;
    }

    @Operation(description = "사용자 읽기기록 저장")
    @PatchMapping("/read")
    public ResponseEntity<MessageAlterResponseDto> setReadRecord(@RequestBody UserNewsReadRequestDto dto){
        User user = userService.getUser();

        if (newsService.setUserNewsRead(user.getUserSeq(), dto)) {
            String alterMsg = userBadgeService.checkNewBadge(user); // 뱃지 획득체크
            if (alterMsg != null){
                return ResponseEntity.status(HttpStatus.OK).body(new MessageAlterResponseDto("읽기기록 저장 성공", alterMsg));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new MessageAlterResponseDto("읽기기록 저장 성공"));
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageAlterResponseDto("읽기기록 저장 오류"));
    }

    @Operation(description = "사용자 풀이기록 저장")
    @PatchMapping("/solve")
    public ResponseEntity<MessageAlterResponseDto> setSolveRecord(@RequestBody UserNewsSolveRequestDto dto){
        User user = userService.getUser();

        if (newsService.setUserNewsSolve(user.getUserSeq(), dto)) {
            String alterMsg = userBadgeService.checkNewBadge(user)  ; // 뱃지 획득체크
            if (alterMsg != null){
                return ResponseEntity.status(HttpStatus.OK).body(new MessageAlterResponseDto("풀이기록 저장 성공", alterMsg));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new MessageAlterResponseDto("풀이기록 저장 성공"));
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageAlterResponseDto("풀이기록 저장 실패"));
    }

}
