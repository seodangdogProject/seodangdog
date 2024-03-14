package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Badge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int badgeSeq;

    // 뱃지 -> 사용자 or 뱃지 -> 사용자 뱃지로 연결은 필요가 없나??
    // 해당 뱃지를 가진 사용자나 사용자들을 정렬할것도 아닌데 필요 없지 않나?

    @Column(length = 3)
    private String badgeName;

    private String badgeImgUrl;

    @Column(length = 50)
    private String badgeDescription;

    private String badgeCondition;
}
