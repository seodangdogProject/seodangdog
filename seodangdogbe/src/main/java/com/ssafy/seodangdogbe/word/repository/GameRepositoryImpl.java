package com.ssafy.seodangdogbe.word.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.word.domain.QUserWord;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public long countUserWords(int userSeq) {
        QUserWord qUserWord = QUserWord.userWord;
        return queryFactory
                .select(qUserWord.count())
                .from(qUserWord)
                .where(qUserWord.user.userSeq.eq(userSeq))
                .fetchOne();
    }

    @Override
    public List<UserWord> findRandomWordsByUserSeq(int userSeq, int limit) {
        QUserWord qUserWord = QUserWord.userWord;
        return queryFactory
                .selectFrom(qUserWord)
                .where(qUserWord.user.userSeq.eq(userSeq))
                .orderBy(Expressions.numberTemplate(Double.class, "function('RAND')").asc())
                .limit(limit)
                .fetch();
    }

    @Override
    public void deleteWordsBySeqsAndUserSeq(List<Long> wordSeqs, int userSeq) {
        QUserWord qUserWord = QUserWord.userWord;
        queryFactory
                .delete(qUserWord)
                .where(qUserWord.wordSeq.in(wordSeqs)
                        .and(qUserWord.user.userSeq.eq(userSeq)))
                .execute();
    }
}
