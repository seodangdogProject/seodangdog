package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageAlterResponseDto;
import com.ssafy.seodangdogbe.keyword.dto.InfoDto;
import com.ssafy.seodangdogbe.keyword.dto.updateWeightFastReqDto;
import com.ssafy.seodangdogbe.keyword.service.KeywordService;
import com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.news.service.NewsService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.service.UserBadgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.seodangdogbe.news.dto.MetaNewsDto.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;
    private final KeywordService keywordService;
    private final UserBadgeService userBadgeService;
    private final FastApiService fastApiService;

    @Operation(description = "newsSeq(mysql pk)로 mongodb에 있는 뉴스 본문 조회")
    @GetMapping("/{newsSeq}/{isAlready}")
    public MetaNewsResponseDto getNewsDetails(@PathVariable(name = "newsSeq") Long newsSeq,
                                              @PathVariable(name = "isAlready") boolean isSpecial){
        // 로그인한 사용자 jwt에서 user가져오기
        User user = userService.getUser();

        MetaNewsResponseDto metaNewsResponseDto = newsService.getNewsDetailsByNewsSeq(newsSeq);
        Map<String, Double> newsKeywordMap = new HashMap<>(metaNewsResponseDto.getNewsSummaryKeyword());
        if(isSpecial){
            keywordService.addKeywordMapWeight(user, newsKeywordMap, 1.5);  // 클릭한 뉴스의 키워드 가중치 * 1.5

        }else{
            keywordService.addKeywordMapWeight(user, newsKeywordMap, 1.5);  // 클릭한 뉴스의 키워드 가중치 * 1.5

        }

        newsService.addViewCount(newsSeq);

        if (!newsService.getUserNewsExist(user.getUserSeq(), newsSeq)){    // 사용자-뉴스 테이블에 기록이 없을 경우 (최초접근 데이터 넣기)
            // fast api
            List<InfoDto> infoDto = new ArrayList<>();
            infoDto.add(new InfoDto(newsSeq, 2.0));
            fastApiService.updateWeigth(new updateWeightFastReqDto(user.getUserSeq(),infoDto)); // 2.0

            newsService.setUserNewsInit(user.getUserSeq(), newsSeq);
        } else {    // 사용자-뉴스 접근기록이 있는 경우
            // fast api
            List<InfoDto> infoDto = new ArrayList<>();
            infoDto.add(new InfoDto(newsSeq, 1.7));
            fastApiService.updateWeigth(new updateWeightFastReqDto(user.getUserSeq(),infoDto)); // 1.7

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

        MetaNewsResponseDto metaNewsResponseDto = newsService.getNewsDetailsByNewsSeq(dto.getNewsSeq());
        Map<String, Double> newsKeywordMap = new HashMap<>(metaNewsResponseDto.getNewsSummaryKeyword());
        keywordService.addKeywordMapWeight(user, newsKeywordMap, 1.2);  // 클릭한 뉴스의 키워드 가중치 * 1.2

        // fast api
        List<InfoDto> infoDto = new ArrayList<>();
        infoDto.add(new InfoDto(dto.getNewsSeq(), 2.0));
        fastApiService.updateWeigth(new updateWeightFastReqDto(user.getUserSeq(),infoDto)); // 2.0

        // 뱃지 획득체크
        if (newsService.setUserNewsSolve(user.getUserSeq(), dto)) {
            String alterMsg = userBadgeService.checkNewBadge(user);
            if (alterMsg != null){
                return ResponseEntity.status(HttpStatus.OK).body(new MessageAlterResponseDto("풀이기록 저장 성공", alterMsg));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new MessageAlterResponseDto("풀이기록 저장 성공"));
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageAlterResponseDto("풀이기록 저장 실패"));
    }

}
