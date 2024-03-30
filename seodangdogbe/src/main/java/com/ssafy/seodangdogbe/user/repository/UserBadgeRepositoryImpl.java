package com.ssafy.seodangdogbe.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.user.domain.QUserBadge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserBadgeRepositoryImpl implements UserBadgeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QUserBadge qUserBadge = QUserBadge.userBadge;

    @Override
    public List<UserBadge> findAllByUser(User user) {
        return jpaQueryFactory.selectFrom(qUserBadge)
                .where(qUserBadge.user.eq(user))
                .orderBy(qUserBadge.badge.badgeSeq.asc())
                .fetch();
    }

    @Override
    public UserBadge findUserBadge(User user, int badgeSeq) {
        return jpaQueryFactory.selectFrom(qUserBadge)
                .where(qUserBadge.user.eq(user),
                        qUserBadge.badge.badgeSeq.eq(badgeSeq))
                .fetchOne();
    }

    // 사용자 대표뱃지 조회
    @Override
    public UserBadge findByUserRepBadge(User user) {
        return jpaQueryFactory.selectFrom(qUserBadge)
                .where(qUserBadge.user.eq(user),
                        qUserBadge.isRepBadge.eq(true))
                .fetchOne();
    }
}
