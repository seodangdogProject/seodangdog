package com.ssafy.seodangdogbe.auth.service;

import com.ssafy.seodangdogbe.auth.dto.ReqUserLoginDto;
import com.ssafy.seodangdogbe.auth.dto.ReqUserSignUpDto;
import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.jwt.JWT;
import com.ssafy.seodangdogbe.jwt.JWTProvider;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public int signUp(ReqUserSignUpDto userSignUpDto) {

        if (userRepository.findByUserId(userSignUpDto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        User user = userSignUpDto.toEntity();
        user.encodePassword(passwordEncoder); // 비밀번호 해싱
        System.out.println(user.getPassword());
        user = userRepository.save(user); // 저장

        return user.getUserSeq();
    }

    public JWT login(ReqUserLoginDto reqUserLoginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(reqUserLoginDto.getUserId(), reqUserLoginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JWT jwt = jwtProvider.generateToken(authentication);
        System.out.println(jwt);
        // 최종 토큰 생성
        return jwt;
    }

    // 회원 seq 가져오기
    // 로그인한 유저가 요청할 때 해당 토큰에 userId값을 넣어놨으므로 해당 userId값으로 member 테이블 조회 -> seq 가져오는 로직
    // 로그인시에 토큰에 저장하게 하는 방법도 고려해보기 -> 한번만 select 하면 되니까..?
    public int getUserSeq() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
        return user.getUserSeq();
    }

    // 회원 seq로 회원 조회
    public User getUserByUserSeq(int userSeq){
        return userRepository.findByUserSeq(userSeq)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
    }
}
