package com.ssafy.seodangdogbe.keyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.keyword.domain.JoinKeyword;
import com.ssafy.seodangdogbe.keyword.domain.QJoinKeyword;
import static com.ssafy.seodangdogbe.keyword.domain.QJoinKeyword.joinKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JoinKeywordRepositoryImpl implements JoinKeywordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<JoinKeyword> findAllKeywords() {
        return queryFactory.selectFrom(joinKeyword)
                .fetch();
    }
}

