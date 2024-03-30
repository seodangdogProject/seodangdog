package com.ssafy.seodangdogbe.user.repository;

import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {
    List<UserBadge> findAllByUser(User user);

    boolean existsByUserAndBadgeBadgeSeq(User user, int badgeSeq);

    //    void saveAll(List<UserBadge> userBadges);
}
