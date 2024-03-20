package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.QKeywordNews;
import com.ssafy.seodangdogbe.news.domain.QNews;
import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.user.domain.QUser;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NewsRecommendRepositoryImpl implements com.ssafy.seodangdogbe.news.repository.NewsRecommendRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QNews qNews = QNews.news;
    private final QUser qUser = QUser.user;
    private final QKeywordNews qKeywordNews = QKeywordNews.keywordNews;
    @Override
    public List<UserRecommendResponseDto> findNewsRecommendations(int userSeq) {


        List<News> keywordnews = queryFactory
                .select(qKeywordNews.news)
                .from(qKeywordNews)
                .fetch();

        for (News news : keywordnews) {
            List<String> newsKeywords = queryFactory
                    .select(qKeywordNews.keyword.keyword)
                    .from(qKeywordNews)
                    .where(qKeywordNews.news.eq(news))
                    .fetch();
        }

        return queryFactory
                .select(Projections.constructor(UserRecommendResponseDto.class,
                        qNews.newsSeq,
                        qNews.newsImgUrl,
                        qNews.newsTitle,
                        qNews.newsDescription,
                        qNews.newsCreatedAt,
                        qNews.keywordNewsList
                ))
                .from(qNews)
                .where(qUser.user.userSeq.eq(userSeq))
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
                        qNews.newsCreatedAt,
                        qNews.keywordNewsList
                ))
                .from(qNews)
                .where(qUser.user.userSeq.eq(userSeq))
                .fetch();
    }


    @Override
    public List<MostSummaryRecommendResponseDto> findMostSummaryNewsRecommendations() {
        List<News> newsList = queryFactory
                .selectFrom(QNews.news)
                .orderBy(QNews.news.countSolve.desc())
                .limit(10)
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            // KeywordNews 리스트에서 키워드만 추출하여 새 리스트로 생성
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword()) // KeywordNews 엔티티 구조에 따라 접근 방식 변경 가능
                    .collect(Collectors.toList());

            return new NewsPreviewListDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    keywords
            );
        }).collect(Collectors.toList());

        return List.of(new MostSummaryRecommendResponseDto(newsPreviewLists));
    }

    @Override
    public List<MostViewRecommendResponseDto> findMostViewNewsRecommendations() {
        List<News> newsList = queryFactory
                .selectFrom(QNews.news)
                .orderBy(QNews.news.countView.desc())
                .limit(10)
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            // KeywordNews 리스트에서 키워드만 추출하여 새 리스트로 생성
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword()) // KeywordNews 엔티티 구조에 따라 접근 방식 변경 가능
                    .collect(Collectors.toList());

            return new NewsPreviewListDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    keywords
            );
        }).collect(Collectors.toList());

        return List.of(new MostViewRecommendResponseDto(newsPreviewLists));
    }
}
