package com.ssafy.seodangdogbe.word.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageAlterResponseDto;
import com.ssafy.seodangdogbe.word.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.seodangdogbe.word.dto.WordApiDto.*;
import static com.ssafy.seodangdogbe.word.dto.WordDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WordController {

    private final UserService userService;
    private final WordService wordService;

    @Operation(description = "단어의 뜻을 가져온다 (순서: mongodb 탐색 -> api 호출)")
    @GetMapping("/news/word/{word}")
    public ResponseEntity<MetaWordDto> getWord(@PathVariable("word") String word) throws Exception {

        // mongodb에 없는 경우
        // ** api 호출해도 단어가 없을 경우 or 예외처리 해주기?!
        if (!wordService.existWord(word)){
            if (wordService.isKor(word)){   // 한글 단어일 경우
                KorApiSearchDto korApiSearchDto = wordService.callStDictSearchApi(word);    // 표준국어대사전 api 호출

                // 결과가 있을 경우
                if (korApiSearchDto != null){
                    // 결과 값 DB에 저장
                    MetaWordDto metaWordDto = new MetaWordDto(korApiSearchDto);
                    wordService.saveMetaWordToMongodb(metaWordDto);

                    return ResponseEntity.ok().body(wordService.findMetaWord(word));
                }

                // 결과가 없을 경우 -> 백과사전 api 호출
            }

            // 백과사전 api 검색
            String wordLang = "kor";
            if (wordService.isEng(word)){   // 영어 단어일 경우
                wordLang = "eng";
            } else {                        // 한글, 영어가 아닌 다른 단어
                wordLang = "other";
            }

            // 백과사전 검색
            EncycApiDto encycApiDto = wordService.callNEncycSearchApi(word);

            // 검색결과가 없는 경우
            if (encycApiDto.getTotal() == 0){
                return ResponseEntity.badRequest().body(new MetaWordDto(word, 0));
            }

            // 검색결과가 있는 경우
            MetaWordDto metaWordDto = new MetaWordDto(word, encycApiDto, wordLang);
            wordService.saveMetaWordToMongodb(metaWordDto);

            return ResponseEntity.ok().body(metaWordDto);
        }

        // mongodb에 있는 경우
        return ResponseEntity.ok().body(wordService.findMetaWord(word));
    }

    @Operation(description = "사용자단어 테이블에서 단어를 삭제한다.")
//    @PatchMapping("/myword/{word}")  // or word
    public ResponseEntity<MessageAlterResponseDto> removeUserWord(@PathVariable("word") String word){
        int userSeq = userService.getUserSeq();
        if (wordService.setDelete(userSeq, word))
            return ResponseEntity.ok().body(new MessageAlterResponseDto("정상적으로 삭제되었습니다."));
        else
            return ResponseEntity.badRequest().body(new MessageAlterResponseDto("단어 삭제에 실패했습니다."));
    }

    @GetMapping("/news/word/encyc")
    public EncycApiDto callNEncyc(@RequestParam String word){
        System.out.println("백과사전 검색");
        return wordService.callNEncycSearchApi(word);
    }

}
