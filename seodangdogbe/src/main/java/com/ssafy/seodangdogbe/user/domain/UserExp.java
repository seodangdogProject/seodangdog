package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserExp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userExpSeq;

    private int newsExp;
    private int wordExp;
    private int inferenceExp;
    private int judgementExp;
    private int summaryExp;
    private int wordGameExp;


}
