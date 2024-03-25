package com.ssafy.seodangdogbe.user.repository;

import com.ssafy.seodangdogbe.user.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    @Override
    Optional<Badge> findById(Integer badgeSeq);
}
