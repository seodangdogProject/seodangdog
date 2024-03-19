package com.ssafy.seodangdogbe.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class MsgResponseDto {
    private String msg;

    public MsgResponseDto(String msg) {
        this.msg = msg;
    }
}
