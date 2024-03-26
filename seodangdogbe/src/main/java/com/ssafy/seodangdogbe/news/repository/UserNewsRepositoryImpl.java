package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.news.domain.QUserNews;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserNewsRepositoryImpl implements UserNewsRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private final QUserNews qUserNews = QUserNews.userNews;
    public List<LocalDateTime> findSolvedUserNews(User user, LocalDateTime start, LocalDateTime end){
        List<LocalDateTime> solvedDateList = queryFactory.selectDistinct(qUserNews.modifiedAt)
                .from(qUserNews)
                .where(qUserNews.user.eq(user),
                        qUserNews.isSolved.eq(true),
                        qUserNews.isDelete.eq(false),
                        qUserNews.modifiedAt.between(start, end))
                .orderBy(qUserNews.modifiedAt.asc())
                .fetch();

        return solvedDateList;
    }

}
