package com.ssafy.seodangdogbe.word.controller;

import com.ssafy.seodangdogbe.word.dto.MyWordResponseDto;
import com.ssafy.seodangdogbe.word.dto.WordDto;
import com.ssafy.seodangdogbe.word.service.MyWordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/myword")
public class MyWordController {
    private final MyWordService myWordService;

    @Autowired
    public MyWordController(MyWordService myWordService) {
        this.myWordService = myWordService;
    }

    // 단어 조회 기능
    @Operation(description = "단어장 - 단어 조회")
    @GetMapping
    public ResponseEntity<MyWordResponseDto> getWords() {
        MyWordResponseDto wordList = myWordService.findAllUserWords();
        return ResponseEntity.ok(wordList);
    }

    @Operation(description = "영어 단어장 - 단어 조회")
    @GetMapping("/EngWord")
    public ResponseEntity<MyWordResponseDto> getNonKoreanWords() {
        MyWordResponseDto nonKoreanWordList = myWordService.findAllEngWords();
        return ResponseEntity.ok(nonKoreanWordList);
    }

    //단어 삭제 기능
    @Operation(description = "단어장 - 단어 삭제")
    @PatchMapping("/{wordSeq}")
    public ResponseEntity<?> deleteWord(@PathVariable Long wordSeq) {
        boolean isDeleted = myWordService.deleteWord(wordSeq);
        if (isDeleted) {
            return ResponseEntity.ok().body("{\"msg\":\"단어장에서 단어 삭제 성공\"}");
        } else {
            return ResponseEntity
                    .status(404)
                    .body("{\"msg\":\"삭제할 단어를 찾지 못했습니다.\"}");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<WordDto.MetaWordDto> searchWord(@RequestParam String word) {
        WordDto.MetaWordDto wordResult = myWordService.searchWord(word);
        if(wordResult != null) {
            return ResponseEntity.ok(wordResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/prefix")
    public ResponseEntity<List<WordDto.MetaWordDto>> getWordsByPrefix(@RequestParam String prefix) {
        List<WordDto.MetaWordDto> words = myWordService.findWordSearch(prefix);
        if (words.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(words);
        }
    }
}
