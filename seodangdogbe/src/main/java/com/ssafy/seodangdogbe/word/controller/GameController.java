package com.ssafy.seodangdogbe.word.controller;

import com.ssafy.seodangdogbe.word.dto.GameActivatedResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameGetProblemResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameResultRequestDto;
import com.ssafy.seodangdogbe.word.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //단어 게임 활성화 기능
    @Operation(description = "단어 게임 - 저장된 단어 10개 이상 시 활성화")
    @GetMapping("/activate")
    public ResponseEntity<GameActivatedResponseDto> checkGameActivation() {
        GameActivatedResponseDto response = gameService.checkGameActivation();
        return ResponseEntity.ok(response);
    }

    //단어 게임 - 10개 단어 추출 기능
    @Operation(description = "단어 게임 - 단어 10개 랜덤 추출")
    @GetMapping("/get-problems")
    public ResponseEntity<GameGetProblemResponseDto> getProblems() {
        GameGetProblemResponseDto response = gameService.getProblems();
        return ResponseEntity.ok(response);
    }

    //단어 게임 후 삭제 기능
    @Operation(description = "단어 게임 - 게임 종료 후 단어장에서 단어 삭제")
    @PatchMapping("/result")
    public ResponseEntity<Void> processGameResult(@RequestBody GameResultRequestDto requestDto) {
        gameService.deleteWords(requestDto);
        return ResponseEntity.ok().build();
    }
}
