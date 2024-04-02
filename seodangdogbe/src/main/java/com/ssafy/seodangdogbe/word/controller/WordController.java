package com.ssafy.seodangdogbe.word.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageAlterResponseDto;
import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.word.dto.UserWordDto;
import com.ssafy.seodangdogbe.word.service.UserWordService;
import com.ssafy.seodangdogbe.word.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.seodangdogbe.word.dto.WordApiDto.*;
import static com.ssafy.seodangdogbe.word.dto.WordDto.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class WordController {

    private final UserService userService;
    private final WordService wordService;
    private final UserWordService userWordService;

    @Operation(description = "단어 뜻 조회 (순서: mongodb 탐색 -> api 호출)")
    @GetMapping("/word/{word}")
    public ResponseEntity<MetaWordDto> getWord(@PathVariable("word") String word) throws Exception {
        int userSeq = userService.getUserSeq();

        // mongodb에 있는 경우
        if (wordService.existsWord(word)) {
            UserWordDto userWordDto = userWordService.findUserWord(userSeq, word);
            MetaWordDto metaWordDto = wordService.findMetaWord(word);
            if (userWordDto != null && !userWordDto.isDelete()){
                System.out.println("단어장에 있음");
                metaWordDto.setExist(true);
            } else {
                System.out.println("단어장에 없음");
                metaWordDto.setExist(false);
            }

            return ResponseEntity.ok().body(metaWordDto);
        }


        // mongodb에 없는 경우
        if (wordService.isKor(word)){   // 한글 단어일 경우
            KorApiSearchDto korApiSearchDto = wordService.callStDictSearchApi(word);    // 표준국어대사전 api 호출

            // api 호출 결과가 있을 경우
            if (korApiSearchDto != null){
                // 결과 값 DB에 저장
                MetaWordDto metaWordDto = new MetaWordDto(korApiSearchDto);
                wordService.saveMetaWordToMongodb(metaWordDto);

                return ResponseEntity.ok().body(metaWordDto);
            }

            // api 호출 결과가 없을 경우 -> 백과사전 api 호출
        }

        // 백과사전 api 검색
        String wordLang = "other";                   // 한글도 영어도 아닌 경우
        if (wordService.isEng(word)){                // 영어 단어일 경우
            wordLang = "eng";
        } else if (wordService.isKor(word)) {        // 한글 단어일 경우
            wordLang = "kor";
        }

        // 백과사전 검색
        EncycApiDto encycApiDto = wordService.callNEncycSearchApi(word);

        // 검색결과가 없는 경우
        if (encycApiDto.getTotal() == 0)
            return ResponseEntity.ok().body(new MetaWordDto(word,0));

        // 검색결과가 있는 경우
        MetaWordDto metaWordDto = new MetaWordDto(word, wordLang, encycApiDto);
        wordService.saveMetaWordToMongodb(metaWordDto);

        metaWordDto.setExist(false);    // mongodb에 없던 단어는 사용자의 단어장에도 있을 수 없다.

        return ResponseEntity.ok().body(metaWordDto);
    }


    @Operation(description = "사용자 단어 스크랩 (단어장에 단어 저장)")
    @PostMapping("/word")
    public ResponseEntity<MessageResponseDto> setUserWord(@RequestBody WordRequestDto wordDto) throws Exception {
        int userSeq = userService.getUserSeq();
        User user = userService.getUser();

        String word = wordDto.getWord();
        // ** 단어가 mongodb에 있는지 확인 -> 없다면 api 호출 후 저장 -> 이렇게 접근할 일 없음
        if (!wordService.existsWord(word))
            getWord(word);


        // 사용자단어 테이블에 이미 있는지 체크
        UserWordDto userWordDto = userWordService.findUserWord(userSeq, word);

        // 있다면 삭제여부 체크 후 저장
        if (userWordDto != null){
            System.out.println("사용자단어 테이블에 이미 있는 단어입니다.");
            if (userWordDto.isDelete()) {    // 삭제되었다면
                System.out.println("삭제된 단어입니다.");
                userWordService.updateUserWordExist(userSeq, word);
                return ResponseEntity.ok().body(new MessageResponseDto("단어 스크랩 성공"));
            } else {    // 이미 스크랩되어있는 상태라면
                System.out.println("이미 스크랩 된 상태입니다.");
                return ResponseEntity.ok().body(new MessageResponseDto("이미 스크랩 되어있는 단어입니다."));
            }
        }

        // 없다면 사용자단어 테이블에 저장
        if (userWordService.setUserWord(user, word) != null){
            return ResponseEntity.ok().body(new MessageResponseDto("단어 스크랩 성공"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponseDto("단어 스크랩 실패"));
        }
    }



//    @Operation(description = "사용자단어 테이블에서 단어를 삭제한다.")
    @PatchMapping("/myword/{word}")  // or word
    public ResponseEntity<MessageAlterResponseDto> removeUserWord(@PathVariable("word") String word){
        int userSeq = userService.getUserSeq();
        if (wordService.setDelete(userSeq, word))
            return ResponseEntity.ok().body(new MessageAlterResponseDto("정상적으로 삭제되었습니다."));
        else
            return ResponseEntity.badRequest().body(new MessageAlterResponseDto("단어 삭제에 실패했습니다."));
    }

//    @Operation(description = "네이버 백과사전 api 호출 테스트")
//    @GetMapping("/word/encyc")
    public EncycApiDto callNEncyc(@RequestParam String word){
        System.out.println("백과사전 검색");
        return wordService.callNEncycSearchApi(word);
    }

    @GetMapping("/word/exist")
    public boolean isExist(@RequestParam String word){
        int userSeq = userService.getUserSeq();
        UserWordDto userWordDto = userWordService.findUserWord(userSeq, word);
        if (userWordDto != null && !userWordDto.isDelete()){
            System.out.println("해당 단어가 단어장에 존재합니다.");
            return true;
        }
        System.out.println("해당 단어가 단어장에 존재하지 않습니다.");
        return false;
    }
}
