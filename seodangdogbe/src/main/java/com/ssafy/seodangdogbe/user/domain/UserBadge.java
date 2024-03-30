package com.ssafy.seodangdogbe.user.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_badge",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_seq", "badge_seq"})})
public class UserBadge extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userBadgeSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badge;

    private boolean isRepBadge = true;

    public UserBadge(Badge badge){
        this.badge = badge;
    }

    public UserBadge(User user, Badge badge) {
        this.user = user;
        this.badge = badge;
        this.isRepBadge = false;
    }
}
