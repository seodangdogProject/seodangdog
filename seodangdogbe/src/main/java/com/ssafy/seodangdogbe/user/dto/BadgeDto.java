package com.ssafy.seodangdogbe.user.dto;

import com.ssafy.seodangdogbe.user.domain.Badge;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
    private int badgeSeq;
    private String badgeName;
    private String badgeImgUrl;
    private String badgeDescription;
    private int badgeCondition;

    public BadgeDto(Badge badge) {
        this.badgeSeq = badge.getBadgeSeq();
        this.badgeName = badge.getBadgeName();
        this.badgeImgUrl = badge.getBadgeImgUrl();
        this.badgeDescription = badge.getBadgeDescription();
        this.badgeCondition = badge.getBadgeCondition();
    }
}
