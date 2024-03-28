package com.ssafy.seodangdogbe.news.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageAlterResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;
import com.ssafy.seodangdogbe.news.service.NewsService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.service.UserBadgeService;
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

import static com.ssafy.seodangdogbe.news.dto.MetaNewsDto.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    public final NewsService newsService;
    public final UserService userService;
    public final WordService wordService;
    public final UserWordService userWordService;
    public final UserBadgeService userBadgeService;

    @Operation(description = "newsSeq(mysql pk)로 mongodb에 있는 뉴스 본문 조회")
    @GetMapping("/{newsSeq}")
    public MetaNewsResponseDto getNewsDetails(@PathVariable(name = "newsSeq") Long newsSeq){
        // 로그인한 사용자 jwt에서 userSeq를 가져오기
        int userSeq = userService.getUserSeq();

        MetaNewsResponseDto metaNewsResponseDto = newsService.getNewsDetailsByNewsSeq(newsSeq);

        if (!newsService.getUserNewsExist(userSeq, newsSeq)){    // 사용자-뉴스 테이블에 기록이 없을 경우(최초접근 데이터 넣기)
            newsService.setUserNewsInit(userSeq, newsSeq);

        } else {    // 사용자-뉴스 접근기록이 있는 경우
            UserNewsResponseDto userRecord = newsService.getReadOrSolveRecord(userSeq, newsSeq);
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

    @Operation(description = "사용자 단어 스크랩 (단어장에 단어 저장)")
    @PostMapping("/word")
    public ResponseEntity<MessageAlterResponseDto> setUserWord(@RequestBody WordRequestDto wordDto) throws Exception {
        int userSeq = userService.getUserSeq();
        User user = userService.getUser();

        String word = wordDto.getWord();
        // ** 단어가 mongodb에 들어와있는지 확인하고, 없다면 api 호출해서 넣어주어야 함 -> 이렇게 접근할 일 없음
        if (!wordService.existWord(word)){
            // 표준국어대사전 API(사전검색 api만 사용) 호출 및 결과 값 받아오기
            WordApiDto.KorApiSearchDto korApiSearchDto = wordService.callStDictSearchApi(word);

            // 결과 값 DB에 저장
            WordDto.MetaWordDto metaWordDto = new WordDto.MetaWordDto(korApiSearchDto);
            wordService.saveMetaWordToMongodb(metaWordDto);
        }


        // 사용자단어 테이블에 이미 있는지 체크
        UserWordDto userWordDto = userWordService.findUserWord(userSeq, word);

        // 있다면 삭제여부 체크 후 저장
        if (userWordDto != null){
            System.out.println("사용자단어 테이블에 이미 있는 단어입니다.");
            if (userWordDto.isDelete()) {    // 삭제되었다면
                System.out.println("삭제된 단어입니다.");
                userWordService.updateUserWordExist(userSeq, word);
                return ResponseEntity.ok().body(new MessageAlterResponseDto("단어 스크랩 성공"));
            } else {    // 이미 스크랩되어있는 상태라면
                System.out.println("이미 스크랩 된 상태입니다.");
                return ResponseEntity.ok().body(new MessageAlterResponseDto("이미 스크랩 되어있는 단어입니다."));
            }
        }

        // 없다면 사용자단어 테이블에 저장
        if (userWordService.setUserWord(user, word) != null){
            return ResponseEntity.ok().body(new MessageAlterResponseDto("단어 스크랩 성공"));
        } else {
            return ResponseEntity.badRequest().body(new MessageAlterResponseDto("단어 스크랩 실패"));
        }
    }





}
