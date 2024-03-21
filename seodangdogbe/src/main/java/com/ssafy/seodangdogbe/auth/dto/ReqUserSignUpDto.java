package com.ssafy.seodangdogbe.auth.dto;

import com.ssafy.seodangdogbe.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserSignUpDto {

    private String userId;
    private String password;
    private String nickname;

    @Builder
    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .role("USER")
                .build();
    }
}