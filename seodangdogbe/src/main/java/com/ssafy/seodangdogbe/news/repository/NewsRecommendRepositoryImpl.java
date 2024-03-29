package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.QKeywordNews;
import com.ssafy.seodangdogbe.news.domain.QNews;
import com.ssafy.seodangdogbe.news.domain.QUserNews;
import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.user.domain.QUser;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.news.service.FastApiService.CbfRecommendResponse;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.seodangdogbe.news.service.FastApiService.MfRecommendResponse;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.seodangdogbe.keyword.domain.QUserKeyword.userKeyword;
import static com.ssafy.seodangdogbe.news.domain.QKeywordNews.keywordNews;
import static com.ssafy.seodangdogbe.news.domain.QNews.news;
import static com.ssafy.seodangdogbe.news.domain.QUserNews.userNews;

@Repository
@RequiredArgsConstructor
public class NewsRecommendRepositoryImpl implements NewsRecommendRepositoryCustom {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final FastApiService fastApiService;
    private final JdbcTemplate jdbcTemplate;

    private final QNews qNews = news;
    private final QUser qUser = QUser.user;
    private final QKeywordNews qKeywordNews = keywordNews;
    private final QUserNews qUserNews = userNews;


    @Override
    @Transactional
    public List<UserRecommendResponseDto> findNewsRecommendations(User user) {
        List<String> reqKeywords = new ArrayList<>();
        List<String> alreadyKeyword = new ArrayList<>();
        List<String> newKeyword = new ArrayList<>();

        List<Long> recommendedNewsSeqs = fastApiService.fetchRecommendations().block().stream()
                .map(CbfRecommendResponse::getNews_seq)
                .collect(Collectors.toList());

        List<News> newsList = queryFactory
                .select(news)
                .from(news)
                .leftJoin(qUserNews).on(qNews.newsSeq.eq(qUserNews.news.newsSeq)).fetchJoin()
                .where(news.newsSeq.in(recommendedNewsSeqs).and(userNews.isSolved.isNull().or(userNews.isSolved.eq(false)))
                ).fetch();

        System.out.println(newsList);
        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
                    .collect(Collectors.toList());

            for (String keyword : keywords) {
                reqKeywords.add(keyword);
            }

            String mediaImgUrl = news.getMedia().getMediaImgUrl();

            return new NewsPreviewListDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    news.getCountView(),
                    mediaImgUrl,
                    keywords
            );
        }).collect(Collectors.toList());

        for (String k : reqKeywords) {
            // 이미 본 기록이 있으면
            boolean exists = queryFactory.selectOne()
                    .from(userKeyword)
                    .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.eq(k))
                    .fetchFirst() != null;

            if (exists) {
                alreadyKeyword.add(k);
            } else {
                newKeyword.add(k);
            }
        }

        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.add(0.44))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(alreadyKeyword))
                .execute();

        entityManager.flush();
        entityManager.clear();

        saveAll(user, newKeyword, 2);

        return List.of(new UserRecommendResponseDto(newsPreviewLists));
    }

    @Override
    public List<OtherRecommendResponseDto> findOtherNewsRecommendations(int userSeq) {
        List<Long> recommendedNewsSeqs = fastApiService.fetchMfRecommendations().block().stream()
                .map(MfRecommendResponse::getNewsSeq)
                .collect(Collectors.toList());

        List<News> newsList = queryFactory
                .select(news)
                .from(news)
                .leftJoin(qUserNews).on(qNews.newsSeq.eq(qUserNews.news.newsSeq)).fetchJoin()
                .where(news.newsSeq.in(recommendedNewsSeqs).and(userNews.isSolved.isNull().or(userNews.isSolved.eq(false)))
                ).fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
                    .collect(Collectors.toList());
            String mediaImgUrl = news.getMedia().getMediaImgUrl();
            return new NewsPreviewListDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    news.getCountView(),
                    mediaImgUrl,
                    keywords
            );
        }).collect(Collectors.toList());

        return List.of(new OtherRecommendResponseDto(newsPreviewLists));
    }


    @Override
    public List<MostSummaryRecommendResponseDto> findMostSummaryNewsRecommendations(int userSeq) {

        List<News> newsList = queryFactory
                .selectFrom(news)
                .orderBy(news.countSolve.desc())
                .limit(10)
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
                    .collect(Collectors.toList());
            String mediaImgUrl = news.getMedia().getMediaImgUrl();
            return new NewsPreviewListDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    news.getCountView(),
                    mediaImgUrl,
                    keywords
            );
        }).collect(Collectors.toList());

        return List.of(new MostSummaryRecommendResponseDto(newsPreviewLists));
    }

    @Override
    public List<MostViewRecommendResponseDto> findMostViewNewsRecommendations(int userSeq) {
        List<News> newsList = queryFactory
                .selectFrom(news)
//                .where(qUserNews.user.userSeq.eq(userSeq))
                .orderBy(news.countView.desc())
                .limit(10)
                .fetch();

        List<NewsPreviewListDto> newsPreviewLists = newsList.stream().map(news -> {
            List<String> keywords = news.getKeywordNewsList().stream()
                    .map(keywordNews -> keywordNews.getKeyword().getKeyword())
                    .collect(Collectors.toList());
            String mediaImgUrl = news.getMedia().getMediaImgUrl();
            return new NewsPreviewListDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    news.getCountView(),
                    mediaImgUrl,
                    keywords
            );
        }).collect(Collectors.toList());

        return List.of(new MostViewRecommendResponseDto(newsPreviewLists));
    }

    @Transactional
    public void saveAll(User user, List<String> keywords, double weight) {
        String sql = "INSERT INTO user_keyword (user_seq, keyword, weight) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement sql, int i) throws SQLException {
                        sql.setInt(1, user.getUserSeq());
                        sql.setString(2, keywords.get(i));
                        sql.setDouble(3, weight);
                    }

                    @Override
                    public int getBatchSize() {
                        return keywords.size();
                    }
                });
    }
}
