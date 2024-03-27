package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.keyword.dto.MFRecommendResponse;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.QKeywordNews;
import com.ssafy.seodangdogbe.news.domain.QNews;
import com.ssafy.seodangdogbe.news.domain.QUserNews;
import com.ssafy.seodangdogbe.news.dto.*;
import com.ssafy.seodangdogbe.user.domain.QUser;
import com.ssafy.seodangdogbe.news.dto.MostViewRecommendResponseDto;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.word.repository.MyWordRepository;
import com.ssafy.seodangdogbe.word.service.WordMeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.news.service.FastApiService.CbfRecommendResponse;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.seodangdogbe.keyword.domain.QUserKeyword.userKeyword;
import static com.ssafy.seodangdogbe.news.domain.QUserNews.userNews;

@Repository
@RequiredArgsConstructor
public class NewsRecommendRepositoryImpl implements NewsRecommendRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final FastApiService fastApiService;
    private final JdbcTemplate jdbcTemplate;

    private final QNews qNews = QNews.news;
    private final QUser qUser = QUser.user;
    private final QKeywordNews qKeywordNews = QKeywordNews.keywordNews;
    private final QUserNews qUserNews = QUserNews.userNews;


    @Override
    public List<UserRecommendResponseDto> findNewsRecommendations(User user) {
        List<String> reqKeywords = new ArrayList<>();
        List<String> alreadyKeyword = new ArrayList<>();
        List<String> newKeyword = new ArrayList<>();

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

            for(String keyword : keywords){
                reqKeywords.add(keyword);
            }

            return new NewsPreviewListDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    keywords
            );
        }).collect(Collectors.toList());

        for (String k : reqKeywords) {
            // 이미 본 기록이 있으면
            boolean exists = queryFactory.selectOne()
                    .from(userKeyword)
                    .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.eq(k))
                    .fetchFirst() != null;

            if(exists){
                alreadyKeyword.add(k);
            }else {
                newKeyword.add(k);
            }
        }

        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.add(1))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(alreadyKeyword))
                .execute();

        saveAll(user, newKeyword, 3);
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
