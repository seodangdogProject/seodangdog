package com.ssafy.seodangdogbe.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoModifyReqDto {
    String nickname;
    String password;
}
