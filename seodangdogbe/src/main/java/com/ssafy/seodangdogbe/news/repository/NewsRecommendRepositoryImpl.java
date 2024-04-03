package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.keyword.domain.Keyword;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.news.service.FastApiService.CbfRecommendResponse;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.seodangdogbe.news.service.FastApiService.MfRecommendResponse;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ssafy.seodangdogbe.keyword.domain.QUserKeyword.userKeyword;
import static com.ssafy.seodangdogbe.news.domain.QKeywordNews.keywordNews;
import static com.ssafy.seodangdogbe.news.domain.QNews.news;
import static com.ssafy.seodangdogbe.news.domain.QUserNews.userNews;

@Repository
@RequiredArgsConstructor
@Slf4j
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

        List<News> unorderedNewsList = queryFactory
                .select(news)
                .from(news)
                .leftJoin(qUserNews).on(qNews.newsSeq.eq(qUserNews.news.newsSeq)).fetchJoin()
                .where(news.newsSeq.in(recommendedNewsSeqs).and(userNews.isSolved.isNull().or(userNews.isSolved.eq(false)))
                ).fetch();

        Map<Long, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < recommendedNewsSeqs.size(); i++) {
            indexMap.put(recommendedNewsSeqs.get(i), i);
        }

        List<News> sortedNewsList = unorderedNewsList.stream()
                .sorted(Comparator.comparingInt(news -> indexMap.getOrDefault(news.getNewsSeq(), -1)))
                .collect(Collectors.toList());

//        System.out.println(newsList);
        List<NewsPreviewListDto> newsPreviewLists = sortedNewsList.stream().map(news -> {
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
                    news.getCountSolve(),
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
    @Transactional
    public UserRecommendResponseDtoV2 findNewsRecommendationsV2(User user) {

        log.info(" ==  추천 요청 == ");
        Map<String, Double> reqKeywords = new HashMap<>();
        Map<String, Double> alreadyKeyword = new HashMap<>();
        Map<String, Double> newKeyword = new HashMap<>();
        List<MainNewsPreviewDto> newsPreviewLists = new ArrayList<>();
        System.out.println(LocalDateTime.now());
        List<CbfRecommendResponse> result = fastApiService.fetchRecommendations().block().stream().toList();
        System.out.println(LocalDateTime.now());
        List<Long> recommendedNewsSeqs = result.stream()
                .map(CbfRecommendResponse::getNews_seq)
                .collect(Collectors.toList());

        List<News> unorderedNewsList = queryFactory
                .select(news)
                .from(news)
                .where(news.newsSeq.in(recommendedNewsSeqs)).fetch();

        Map<Long, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < recommendedNewsSeqs.size(); i++) {
            indexMap.put(recommendedNewsSeqs.get(i), i);
        }
        List<News> sortedNewsList = unorderedNewsList.stream()
                .sorted(Comparator.comparingInt(n -> indexMap.getOrDefault(n.getNewsSeq(), -1)))
                .collect(Collectors.toList());

        for(int i = 0; i < sortedNewsList.size(); i++){
            Map<String, Double> keywords = result.get(i).getNews_keyword();
            News news = sortedNewsList.get(i);
            String mediaImgUrl = news.getMedia().getMediaImgUrl();
            reqKeywords.putAll(keywords);

            newsPreviewLists.add(new MainNewsPreviewDto(
                    news.getNewsSeq(),
                    news.getNewsImgUrl(),
                    news.getNewsTitle(),
                    news.getNewsDescription(),
                    news.getNewsCreatedAt(),
                    news.getCountView(),
                    news.getCountSolve(),
                    mediaImgUrl,
                    keywords
            ))
            ;
        }

//        List<MainNewsPreviewDto> newsPreviewLists = sortedNewsList.stream().map(news -> {
//
//            Map<String, Double> keywords = news.get()
//
//                    .flatMap(n -> n.getNews_keyword().entrySet().stream())
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue));
//
//            System.out.println(keywords);
//
//            reqKeywords.putAll(keywords);
//
//            String mediaImgUrl = news.getMedia().getMediaImgUrl();
//
//            return new MainNewsPreviewDto(
//                    news.getNewsSeq(),
//                    news.getNewsImgUrl(),
//                    news.getNewsTitle(),
//                    news.getNewsDescription(),
//                    news.getNewsCreatedAt(),
//                    news.getCountView(),
//                    mediaImgUrl,
//                    keywords
//            );
//        }).collect(Collectors.toList());

        // 사용자가 가진 키워드 리스트
        List<String> keywordList = queryFactory
                .select(Projections.constructor(String.class, userKeyword.keyword.keyword))
                .from(userKeyword)
                .where(userKeyword.user.eq(user))
                .fetch();

//        System.out.println("======================");
//        System.out.println(keywordList);
//        System.out.println(keywordList.size());

        for (String k : reqKeywords.keySet()) {
           if (keywordList.contains(k)){     // 사용자가 이미 갖고있는 키워드면
               alreadyKeyword.put(k, reqKeywords.get(k));
           }else {                          // 사용자가 갖고있지 않는 키워드면
               newKeyword.put(k, reqKeywords.get(k));
           }
        }
//
//        System.out.println("======================");
//        System.out.println(newKeyword);
//        System.out.println(alreadyKeyword);

        // 기존에 있는 키워드는 -> * 1.4
//        queryFactory
//                .update(userKeyword)
//                .set(userKeyword.weight, userKeyword.weight.multiply(1.4).multiply(1e10).divide(1e10))
//                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(alreadyKeyword.keySet()))
//                .execute();
//
//        // 새로 들어온 키워드
         saveAllV2(user, newKeyword);
//
//        entityManager.flush();
//        entityManager.clear();

        System.out.println(LocalDateTime.now());
        return new UserRecommendResponseDtoV2(newsPreviewLists);
    }

    @Override
    public List<OtherRecommendResponseDto> findOtherNewsRecommendations(int userSeq) {
        List<Long> recommendedNewsSeqs = fastApiService.fetchMfRecommendations().block().stream()
                .map(MfRecommendResponse::getNewsSeq)
                .collect(Collectors.toList());

        List<News> unorderedNewsList = queryFactory
                .select(news)
                .from(news)
                .leftJoin(qUserNews).on(qNews.newsSeq.eq(qUserNews.news.newsSeq)).fetchJoin()
                .where(news.newsSeq.in(recommendedNewsSeqs)
                ).fetch();

        // 추천된 순서대로 뉴스 목록 정렬
        Map<Long, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < recommendedNewsSeqs.size(); i++) {
            indexMap.put(recommendedNewsSeqs.get(i), i);
        }

        List<News> sortedNewsList = unorderedNewsList.stream()
                .sorted(Comparator.comparingInt(news -> indexMap.getOrDefault(news.getNewsSeq(), -1)))
                .collect(Collectors.toList());

        List<NewsPreviewListDto> newsPreviewLists = sortedNewsList.stream().map(news -> {
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
                    news.getCountSolve(),
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
                .limit(50)
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
                    news.getCountSolve(),
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
                .limit(50)
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
                    news.getCountSolve(),
                    mediaImgUrl,
                    keywords
            );
        }).collect(Collectors.toList());

        return List.of(new MostViewRecommendResponseDto(newsPreviewLists));
    }

    @Transactional
    public void saveAllV2(User user, Map<String, Double> keywords) {
        List<String> keywordsList = keywords.keySet().stream().toList();

        String sql = "INSERT INTO user_keyword (user_seq, keyword, weight) " +
                "VALUES (?, ?, ROUND(?, 10))";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement sql, int i) throws SQLException {
                        sql.setInt(1, user.getUserSeq());
                        sql.setString(2, keywordsList.get(i));
                        sql.setDouble(3, keywords.get(keywordsList.get(i)) * 1.5);
                    }

                    @Override
                    public int getBatchSize() {
                        return keywords.size();
                    }
                });
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
