package com.ssafy.seodangdogbe.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MsgResponseDto {
    private String msg;
    private String alertMsg;

    public MsgResponseDto(String msg) {
        this.msg = msg;
    }

}
