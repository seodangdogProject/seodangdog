package com.ssafy.seodangdogbe.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.user.domain.QUserWord;
import com.ssafy.seodangdogbe.user.domain.UserWord;
import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MyWordRepositoryImpl implements MyWordRepositoryCustom {
    private final JPAQueryFactory queryFactory;



    // MyWordRepositoryImpl 구현체
    @Override
    public List<UserWord> findAllUserWords(int userSeq) {
        QUserWord qUserWord = QUserWord.userWord;
        return queryFactory
                .select(qUserWord)
                .from(qUserWord)
                .where(qUserWord.user.userSeq.eq(userSeq))
                .fetch();


    }
}


