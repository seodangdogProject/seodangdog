package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.news.domain.QKeywordNews;
import com.ssafy.seodangdogbe.news.domain.QNews;
import com.ssafy.seodangdogbe.news.dto.MostSummaryRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.OtherRecommendResponseDto;
import com.ssafy.seodangdogbe.news.dto.UserRecommendResponseDto;
import com.ssafy.seodangdogbe.user.domain.QUserWord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsRecommendRepositoryImpl implements NewsRecommendRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final QNews qNews = QNews.news;
    private final QUserWord qUserWord = QUserWord.userWord;
    private final QKeywordNews qKeywordNews = QKeywordNews.keywordNews;
    @Override
    public List<UserRecommendResponseDto> findNewsRecommendations(int userSeq) {

        return queryFactory
                .select(Projections.constructor(UserRecommendResponseDto.class,
                        qNews.newsSeq,
                        qNews.newsImgUrl,
                        qNews.newsTitle,
                        qNews.newsDescription,
                        qNews.newsCreatedAt

                ))
                .from(qNews)
                .where(qUserWord.user.userSeq.eq(userSeq))
                .fetch();
    }
    @Override
    public List<OtherRecommendResponseDto> findOtherNewsRecommendations(int userSeq) {

        return queryFactory
                .select(Projections.constructor(OtherRecommendResponseDto.class,
                        qNews.newsSeq,
                        qNews.newsImgUrl,
                        qNews.newsTitle,
                        qNews.newsDescription,
                        qNews.newsCreatedAt

                ))
                .from(qNews)
                .where(qUserWord.user.userSeq.eq(userSeq))
                .fetch();
    }


    @Override
    public List<MostSummaryRecommendResponseDto> findMostSummaryNewsRecommendations(int userSeq) {

        return queryFactory
                .select(Projections.constructor(MostSummaryRecommendResponseDto.class,
                        qNews.newsSeq,
                        qNews.newsImgUrl,
                        qNews.newsTitle,
                        qNews.newsDescription,
                        qNews.newsCreatedAt,
                        qNews.countView

                ))
                .from(qNews)
                .where(qUserWord.user.userSeq.eq(userSeq))
                .orderBy(qNews.countSolve.desc())
                .fetch();
    }

    @Override
    public List<MostViewRecommendResponseDto> findMostViewNewsRecommendations(int userSeq) {

        return queryFactory
                .select(Projections.constructor(MostViewRecommendResponseDto.class,
                        qNews.newsSeq,
                        qNews.newsImgUrl,
                        qNews.newsTitle,
                        qNews.newsDescription,
                        qNews.newsCreatedAt,
                        qNews.countView

                ))
                .from(qNews)
                .where(qUserWord.user.userSeq.eq(userSeq))
                .orderBy(qNews.countView.desc())
                .fetch();
    }

}
