package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;
import com.ssafy.seodangdogbe.user.repository.MyWordRepository;
import com.ssafy.seodangdogbe.user.service.MyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/myword")
public class MyWordController {
    private final MyWordService myWordService;

    @Autowired
    public MyWordController(MyWordService myWordService) {
        this.myWordService = myWordService;
    }
    // 단어 조회 기능
    @GetMapping
    public ResponseEntity<MyWordResponseDto> getWords(@RequestParam int userSeq) {
        MyWordResponseDto wordList = myWordService.findAllUserWords(userSeq);
        return ResponseEntity.ok(wordList);
    }

    //단어 삭제 기능
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
}
