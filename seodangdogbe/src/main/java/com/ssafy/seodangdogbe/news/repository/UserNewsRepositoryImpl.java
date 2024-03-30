package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.news.domain.QUserNews;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserNewsRepositoryImpl implements UserNewsRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private final QUserNews qUserNews = QUserNews.userNews;

    public List<LocalDateTime> findSolvedDateList(User user, LocalDateTime start, LocalDateTime end){
        return queryFactory.selectDistinct(qUserNews.modifiedAt)  // 안겹치게 하려면 Distinct 붙이기
                .from(qUserNews)
                .where(qUserNews.user.eq(user),
                        qUserNews.isSolved.eq(true),
                        qUserNews.modifiedAt.between(start, end))
                .orderBy(qUserNews.modifiedAt.asc())
                .fetch();
    }

    public Integer countSolvedDate(User user){
        return queryFactory.selectDistinct(qUserNews.modifiedAt)
                .from(qUserNews)
                .where(qUserNews.user.eq(user),
                        qUserNews.isSolved.eq(true))
                .fetch().size();
    }


    public UserNews findRecentViewUserNews(User user){
        return queryFactory.selectFrom(qUserNews)
                .where(qUserNews.user.eq(user),
                        qUserNews.isSolved.eq(false))
                .orderBy(qUserNews.modifiedAt.desc())
                .fetchFirst();
    }

    public UserNews findRecentSolvedUserNews(User user){
        return queryFactory.selectFrom(qUserNews)
                .where(qUserNews.user.eq(user),
                        qUserNews.isSolved.eq(true))
                .orderBy(qUserNews.modifiedAt.desc())
                .fetchFirst();
    }

}
