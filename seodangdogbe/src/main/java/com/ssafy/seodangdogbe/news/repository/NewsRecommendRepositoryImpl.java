package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.QKeywordNews;
import com.ssafy.seodangdogbe.news.domain.QNews;
import com.ssafy.seodangdogbe.news.domain.QUserNews;
import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.user.domain.QUser;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.word.repository.MyWordRepository;
import com.ssafy.seodangdogbe.word.service.WordMeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.news.service.FastApiService.CbfRecommendResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NewsRecommendRepositoryImpl implements NewsRecommendRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final FastApiService fastApiService;
    private final QNews qNews = QNews.news;
    private final QUser qUser = QUser.user;
    private final QKeywordNews qKeywordNews = QKeywordNews.keywordNews;
    private final QUserNews qUserNews = QUserNews.userNews;


    @Override
    public List<UserRecommendResponseDto> findNewsRecommendations(int userSeq) {
        List<Long> recommendedNewsSeqs = fastApiService.fetchRecommendations().block().stream()
                .map(CbfRecommendResponse::getNewsSeq)
                .collect(Collectors.toList());

        List<News> newsList = queryFactory
                .selectFrom(QNews.news)
                .where(QNews.news.newsSeq.in(recommendedNewsSeqs)
                    .and(QUserNews.userNews.isSolved.eq(false)))
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
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

        return List.of(new UserRecommendResponseDto(newsPreviewLists));
    }
    @Override
    public List<OtherRecommendResponseDto> findOtherNewsRecommendations(int userSeq) {
        List<Long> recommendedNewsSeqs = fastApiService.fetchRecommendations().block().stream()
                .map(CbfRecommendResponse::getNewsSeq)
                .collect(Collectors.toList());

        List<News> newsList = queryFactory
                .selectFrom(QNews.news)
                .where(QNews.news.newsSeq.in(recommendedNewsSeqs)
                        .and(QUserNews.userNews.isSolved.eq(false)))
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
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

        return List.of(new OtherRecommendResponseDto(newsPreviewLists));
    }


    @Override
    public List<MostSummaryRecommendResponseDto> findMostSummaryNewsRecommendations(int userSeq) {
        List<News> newsList = queryFactory
                .selectFrom(QNews.news)
                .orderBy(QNews.news.countSolve.desc())
                .limit(10)
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
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
    public List<MostViewRecommendResponseDto> findMostViewNewsRecommendations(int userSeq) {
        List<News> newsList = queryFactory
                .selectFrom(QNews.news)
//                .where(qUserNews.user.userSeq.eq(userSeq))
                .orderBy(QNews.news.countView.desc())
                .limit(10)
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
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
