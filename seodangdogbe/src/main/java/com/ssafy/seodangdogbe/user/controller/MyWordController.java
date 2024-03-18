package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;
import com.ssafy.seodangdogbe.user.repository.MyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/myword")
public class MyWordController {
    private final MyWordRepository myWordRepository;

    @Autowired
    public MyWordController(MyWordRepository myWordRepository) {
        this.myWordRepository = myWordRepository;
    }

    // 단어 조회 기능
    @GetMapping
    public List<MyWordResponseDto> getWords() {
        List<MyWordResponseDto> wordList = new ArrayList<>();
        int userSeq = 1; // 사용자 식별자는 여기서 임의로 설정
        return myWordRepository.findAllUserWords(userSeq);
    }

    //단어 삭제 기능
    @DeleteMapping("/{wordSeq}")
    public ResponseEntity<?> deleteWord(@PathVariable("wordSeq") Long wordSeq) {
        Optional<?> word = myWordRepository.findById(wordSeq);
        if (!word.isPresent()) {
            // 단어를 찾을 수 없을 때 404 상태 코드와 메시지 반환
            return ResponseEntity
                    .status(404)
                    .body("{\"msg\" : \"삭제할 단어를 찾지 못했습니다.\"}");
        }

        myWordRepository.deleteById(wordSeq);
        return ResponseEntity.ok("단어장에서 단어 삭제 성공");
    }
}
