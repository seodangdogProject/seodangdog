package com.ssafy.seodangdogbe.user.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserExp extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userExpSeq;

    private int newsExp = 0;    //본 뉴스 개수
    private int wordExp = 0;    //어휘 문제 정답 개수
    private int inferenceExp = 0;   //추론 문제 정답 개수
    private int judgementExp = 0;   //판단 문제 정답 개수
    private int summaryExp = 0;     //요약 작성 개수
    private int wordGameExp = 0;    //단어 게임 정답 개수


}
