package com.ssafy.seodangdogbe.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JWTDto {
    private String accessToken;
    private String refreshToken;
}