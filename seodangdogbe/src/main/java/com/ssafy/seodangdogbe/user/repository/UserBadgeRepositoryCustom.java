package com.ssafy.seodangdogbe.user.repository;

import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;

import java.util.List;
import java.util.Optional;

public interface UserBadgeRepositoryCustom {

    List<UserBadge> findAllByUser(User user);

    UserBadge findUserBadge(User user, int badgeSeq);

    UserBadge findByUserRepBadge(User user);
}
