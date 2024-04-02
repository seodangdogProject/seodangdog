package com.ssafy.seodangdogbe.news.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.QKeywordNews;
import com.ssafy.seodangdogbe.news.domain.QNews;
import com.ssafy.seodangdogbe.news.domain.QUserNews;
import com.ssafy.seodangdogbe.news.dto.NewsPreviewListDto;
import com.ssafy.seodangdogbe.news.dto.RecentNotSolvedDto;
import com.ssafy.seodangdogbe.news.dto.RecentSolvedDto;
import com.ssafy.seodangdogbe.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MypageRecentNewsRepositoryImpl implements MypageRecentNewsRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final QNews qNews = QNews.news;
    private final QUser qUser = QUser.user;
    private final QKeywordNews qKeywordNews = QKeywordNews.keywordNews;
    private final QUserNews qUserNews = QUserNews.userNews;

    @Override
    public List<RecentSolvedDto> findRecentSolvedNews(int userSeq) {
        List<News> newsList = queryFactory
                .select(QNews.news)
                .from(QNews.news)
                .join(qUserNews).on(QNews.news.newsSeq.eq(QUserNews.userNews.news.newsSeq))
                .where(QUserNews.userNews.user.userSeq.eq(userSeq)
                        .and(QUserNews.userNews.isSolved.eq(true)))
                .orderBy(QNews.news.modifiedAt.desc())
                .limit(20)
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

        return List.of(new RecentSolvedDto(newsPreviewLists));
    }

    @Override
    public List<RecentNotSolvedDto> findRecentNotSolvedNews(int userSeq) {
        List<News> newsList = queryFactory
                .select(QNews.news)
                .from(QNews.news)
                .join(qUserNews).on(QNews.news.newsSeq.eq(QUserNews.userNews.news.newsSeq))
                .where(QUserNews.userNews.user.userSeq.eq(userSeq)
                        .and(QUserNews.userNews.isSolved.eq(false)))
                .orderBy(QNews.news.modifiedAt.desc())
                .limit(20)
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

        return List.of(new RecentNotSolvedDto(newsPreviewLists));
    }
}
