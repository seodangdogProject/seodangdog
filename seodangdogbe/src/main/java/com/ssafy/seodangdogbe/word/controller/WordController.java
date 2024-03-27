package com.ssafy.seodangdogbe.word.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.word.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.seodangdogbe.word.dto.WordApiDto.*;
import static com.ssafy.seodangdogbe.word.dto.WordDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WordController {

    public final UserService userService;
    public final WordService wordService;

    @Operation(description = "단어의 뜻을 가져온다 (순서: mongodb 탐색 -> api 호출)")
    @GetMapping("/news/word/{word}")
    public MetaWordDto getWord(@PathVariable("word") String word) throws Exception {

        // mongodb에 있는 단어인지 탐색
        // ** api 호출해도 단어가 없을 경우 or 예외처리 해주기?!
        if (!wordService.existWord(word)){  // mongodb에 없는 경우
            if (wordService.isKor(word)){   // 한글 단어일 경우 -> 표준국어대사전 API(사전검색 api만 사용) 호출
                KorApiSearchDto korApiSearchDto = wordService.callStDictSearchApi(word);

                if (korApiSearchDto != null){   // 표준국어대사전 api 결과가 있을 경우
                    // 결과 값 DB에 저장
                    MetaWordDto metaWordDto = new MetaWordDto(korApiSearchDto);
                    wordService.saveMetaWordToMongodb(metaWordDto);

                    return metaWordDto;
                }
                // ** 표준국어대사전에 없는 단어일 경우 -> 백과사전 api 호출
            }

            // ** 백과사전 api 검색
            EncycApiDto encycApiDto = wordService.callNEncycSearchApi(word);

            System.out.println("********* total : "+encycApiDto.getTotal());
            System.out.println("*********"+encycApiDto.getItems().get(0).getTitle());

            String wordLang;
            if (wordService.isEng(word))
                wordLang = "eng";
            else    // ** 아예 db에 넣지말고 반환하지말기
                wordLang = "other";

            MetaWordDto metaWordDto = new MetaWordDto(word, encycApiDto, wordLang);
            wordService.saveMetaWordToMongodb(metaWordDto);

            return metaWordDto;
        }

        // mongodb에 있는 경우
        return wordService.findMetaWord(word);
    }

    @Operation(description = "사용자단어 테이블에서 단어를 삭제한다.")
//    @PatchMapping("/myword/{word}")  // or word
    public ResponseEntity<MsgResponseDto> removeUserWord(@PathVariable("word") String word){
        int userSeq = userService.getUserSeq();
        if (wordService.setDelete(userSeq, word))
            return ResponseEntity.ok().body(new MsgResponseDto("정상적으로 삭제되었습니다."));
        else
            return ResponseEntity.badRequest().body(new MsgResponseDto("단어 삭제에 실패했습니다."));
    }




}
