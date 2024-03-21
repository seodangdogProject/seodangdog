package com.ssafy.seodangdogbe.test;

import com.ssafy.seodangdogbe.auth.dto.ReqUserLoginDto;
import com.ssafy.seodangdogbe.auth.dto.ReqUserSignUpDto;
import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.jwt.JWT;
import com.ssafy.seodangdogbe.jwt.JWTDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class testController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Integer> join(@RequestBody ReqUserSignUpDto userSignUpDto) {
        log.info("*** join 요청 *** ");
        int id =  userService.signUp(userSignUpDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTDto> login(@RequestBody ReqUserLoginDto userLoginDto){
        log.info("*** login 요청 *** ");
        JWT token = userService.login(userLoginDto);

        JWTDto responseDto =
                JWTDto.builder()
                        .accessToken(token.getGrantType() + " " + token.getAccessToken())
                        .refreshToken(token.getGrantType() + " " + token.getRefreshToken())
                        .build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/test") // login 후 test용도
    public ResponseEntity<String> test(){
        log.info("*** test 요청 *** ");
        System.out.println(userService.getUserSeq());
        return ResponseEntity.ok("ok");
    }
}