package com.ssafy.seodangdogbe.word.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameActivatedResponseDto {
    private boolean isActivated = false;

    private long wordCount;
}
