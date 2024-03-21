package com.ssafy.seodangdogbe.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JWT {
    private String grantType; // 우리는 Bearer 만 사용할 것임
    private String accessToken;
    private String refreshToken;
}