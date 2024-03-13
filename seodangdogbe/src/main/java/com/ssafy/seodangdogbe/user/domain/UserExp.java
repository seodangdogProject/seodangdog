package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserExp {

    @Id @GeneratedValue
    private int userExpSeq;

    // 양방향 아니면 안해도 되나? 사용자 경험치에서 사용자 정보를 가져올 일이 없지않나?
//    @OneToOne(mappedBy = "userExp")
//    private User user;

    private int newsExp;
    private int wordExp;
    private int inferenceExp;
    private int judgementExp;
    private int summaryExp;
    private int wordGameExp;





}
