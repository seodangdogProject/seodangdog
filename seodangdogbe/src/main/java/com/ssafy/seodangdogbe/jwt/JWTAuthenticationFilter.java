package com.ssafy.seodangdogbe.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends GenericFilterBean {

    private final JWTProvider jwtProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info(" *** do Filter *** ");

        // Header 에서 토큰 추출하기
        String token = getToken((HttpServletRequest) request);
        // Token 유효성 검사하기
        if(token != null && jwtProvider.validateToken(token)){
            // 토큰이 유효한 경우
            // 1. Auth 객체 가져오기
            Authentication authentication = jwtProvider.getAuthentication(token);
            System.out.println(authentication);
            // 2. SecurityContextHolder 에 저장하기
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response); // 다음 필터로 요청을 전달함
    }


    // Request Header에서 토큰 정보 추출
    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}