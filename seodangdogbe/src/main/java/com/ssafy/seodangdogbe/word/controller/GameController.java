package com.ssafy.seodangdogbe.word.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.service.UserBadgeService;
import com.ssafy.seodangdogbe.word.dto.GameActivatedResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameGetProblemResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameResultRequestDto;
import com.ssafy.seodangdogbe.word.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    public final UserService userService;
    public final GameService gameService;
    public final UserBadgeService userBadgeService;

    @Operation(description = "단어 게임 - 저장된 단어 10개 이상 시 활성화")
    @GetMapping("/activate")
    public ResponseEntity<GameActivatedResponseDto> checkGameActivation() {
        GameActivatedResponseDto response = gameService.checkGameActivation();
        return ResponseEntity.ok(response);
    }

    @Operation(description = "단어 게임 - 단어 10개 랜덤 추출")
    @GetMapping("/get-problems")
    public ResponseEntity<GameGetProblemResponseDto> getProblems() {
        GameGetProblemResponseDto response = gameService.getProblems();
        return ResponseEntity.ok(response);
    }

    @Operation(description = "단어 게임 - 게임 종료 후 단어장에서 단어 삭제")
    @PatchMapping("/result")
    public ResponseEntity<MsgResponseDto> processGameResult(@RequestBody GameResultRequestDto requestDto) {
        User user = userService.getUser();

//        gameService.deleteWords(requestDto);
        gameService.deleteWords(user.getUserSeq(), requestDto);

        String alterMsg = userBadgeService.checkNewBadge(user); // 뱃지 획득체크
        if (alterMsg != null){
            return ResponseEntity.ok().body(new MsgResponseDto("단어 삭제 성공", alterMsg));
        }
        
        return ResponseEntity.ok().body(new MsgResponseDto("단어 삭제 성공"));
    }
}
