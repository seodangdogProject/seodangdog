package com.ssafy.seodangdogbe.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.user.domain.QUserWord;
import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyWordRepositoryImpl implements MyWordRepositoryCustom { // 인터페이스 구현이 누락되어 추가했습니다.
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyWordResponseDto> findAllUserWords(int userSeq) {
        QUserWord userWord = QUserWord.userWord;

        return queryFactory
                .select(Projections.constructor(MyWordResponseDto.class,
                        userWord.wordSeq,
                        userWord.word
                        //userWord.mean
                ))
                .from(userWord)
                .where(userWord.user.userSeq.eq(userSeq))
                .fetch();
    }
}


