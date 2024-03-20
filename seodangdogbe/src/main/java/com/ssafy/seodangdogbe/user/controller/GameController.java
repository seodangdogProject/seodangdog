package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.user.dto.GameActivatedResponseDto;
import com.ssafy.seodangdogbe.user.dto.GameGetProblemResponseDto;
import com.ssafy.seodangdogbe.user.dto.GameResultRequestDto;
import com.ssafy.seodangdogbe.user.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(description = "단어 게임 활성화 기능")
    @GetMapping("/activate")
    public ResponseEntity<GameActivatedResponseDto> checkGameActivation(@RequestParam int userSeq) {
        GameActivatedResponseDto response = gameService.checkGameActivation(userSeq);
        return ResponseEntity.ok(response);
    }

    //단어 게임 - 10개 단어 추출 기능
    @GetMapping("/get-problems")
    public ResponseEntity<GameGetProblemResponseDto> getProblems(@RequestParam int userSeq) {
        GameGetProblemResponseDto response = gameService.getProblems(userSeq);
        return ResponseEntity.ok(response);
    }

    //단어 게임 후 삭제 기능
    @PatchMapping("/result")
    public ResponseEntity<Void> processGameResult(@RequestBody GameResultRequestDto requestDto) {
        gameService.deleteWords(requestDto);
        return ResponseEntity.ok().build();
    }
}
