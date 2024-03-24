package com.ssafy.seodangdogbe.word.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.JsonConverter;
import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.WordItem;
import com.ssafy.seodangdogbe.word.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static com.ssafy.seodangdogbe.word.dto.KorApiDto.*;
import static com.ssafy.seodangdogbe.word.dto.WordDto.*;

@RestController
@RequiredArgsConstructor
public class WordController {

    public final UserService userService;
    public final WordService wordService;

    @Operation(description = "단어의 뜻을 가져온다 (순서: mongodb 탐색 -> api 호출)")
    @GetMapping("/api/news/word/{word}")
    public MetaWordDto getWord(@PathVariable("word") String word) throws Exception {
        if (wordService.isKor(word)){   // 한글 단어일 경우
            // mongodb에 있는 단어인지 탐색
            // ** 단어가 없을 경우 or 예외처리 해주기?!
            if (!wordService.isExistWord(word)){  // mongodb에 없으면

                // 표준국어대사전 API(사전검색 api만 사용) 호출 및 결과 값 받아오기
                KorApiSearchDto korApiSearchDto = wordService.callStDictSearchApi(word);

                // 결과 값 DB에 저장
                MetaWordDto metaWordDto = new MetaWordDto(korApiSearchDto);
                wordService.saveMetaWordToMongodb(metaWordDto);

                return metaWordDto;
            }

            MetaWordDto metaWordDto = wordService.findMetaWord(word);
            return metaWordDto;
        } else {    // 영어 단어일 경우  // 영어단어 저장 형식을 다르게 할건지 뭔지
            // 영어 단어 처리
            wordService.getEngWord(word);
            return null;
        }
    }

    @Operation(description = "사용자단어 테이블에서 단어를 삭제한다.")
    @PatchMapping("/api/myword/{word}")  // or word
    public ResponseEntity<MsgResponseDto> removeUserWord(@PathVariable("word") String word){
        int userSeq = userService.getUserSeq();
        if (wordService.setDelete(userSeq, word))
            return ResponseEntity.ok().body(new MsgResponseDto("정상적으로 삭제되었습니다."));
        else
            return ResponseEntity.badRequest().body(new MsgResponseDto("단어 삭제에 실패했습니다."));
    }


}
