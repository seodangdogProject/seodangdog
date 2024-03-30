package com.ssafy.seodangdogbe.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageAlterResponseDto {
    private String msg;
    private String alertMsg;

    public MessageAlterResponseDto(String msg) {
        this.msg = msg;
    }

}
