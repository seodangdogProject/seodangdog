package com.ssafy.seodangdogbe.auth.dto;

import com.ssafy.seodangdogbe.user.domain.User;
import lombok.*;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqUserSignUpDto {

    private String userId;
    private String password;
    private String nickname;
    private List<String> keywords;

    @Builder
    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .nickname(nickname)
                .role("USER")
                .build();
    }

    @Override
    public String toString() {
        return "ReqUserSignUpDto{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", keywords=" + keywords +
                '}';
    }

}