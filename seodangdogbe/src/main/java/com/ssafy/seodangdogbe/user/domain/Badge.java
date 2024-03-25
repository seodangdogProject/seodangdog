package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Badge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int badgeSeq;

    @Column(length = 3)
    private String badgeName;

    private String badgeImgUrl;

    @Column(length = 50)
    private String badgeDescription;

    private int badgeCondition;

    public Badge(String badgeName, String badgeImgUrl, String badgeDescription, int badgeCondition) {
        this.badgeName = badgeName;
        this.badgeImgUrl = badgeImgUrl;
        this.badgeDescription = badgeDescription;
        this.badgeCondition = badgeCondition;
    }
}
