package com.ssafy.seodangdogbe.word.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.word.domain.QUserWord;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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


