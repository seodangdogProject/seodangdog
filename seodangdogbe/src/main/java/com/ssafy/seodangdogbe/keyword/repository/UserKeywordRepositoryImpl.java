package com.ssafy.seodangdogbe.keyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.keyword.dto.MFRecommendResponse;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ssafy.seodangdogbe.keyword.domain.QUserKeyword.userKeyword;
import static com.ssafy.seodangdogbe.news.domain.QUserNews.userNews;

@Repository
@RequiredArgsConstructor
public class UserKeywordRepositoryImpl implements UserKeywordRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;
    private final FastApiService fastApiService;

    @Override
    @Transactional
    public void decrementKeywordWeight(User user, List<NewsRefreshReqDto> newsRefreshReqDtoList, double highWeight, double rowWeight) {

        List<Long> seeNewsSeq = new ArrayList<>();
        List<Long> notSeenNewsSeq = new ArrayList<>();
        List<MFRecommendResponse> mfRecommendResponseList = new ArrayList<>();
        // 만약 안본 두개의 뉴스의 키워드가 중복된다면 -> 두번 내리는거 맞는지 물어봐야됨
        List<String> seenNewsKeyword = new ArrayList<>();
        List<String> notSeenNewsKeyword = new ArrayList<>();

        for (NewsRefreshReqDto news : newsRefreshReqDtoList) {
            // 이미 본 기록이 있으면
            boolean exists = queryFactory.selectOne()
                    .from(userNews)
                    .where(userNews.user.eq(user), userNews.news.newsSeq.eq(news.getNewSeq()))
                    .fetchFirst() != null;

            if (!exists) { // 존재하지않음 -> 안봄
                notSeenNewsSeq.add(news.getNewSeq());
                mfRecommendResponseList.add(new MFRecommendResponse(user.getUserSeq(), news.getNewSeq(), news.getSimilarity(), highWeight));
                for(String str : news.getKeyword()) {
                    notSeenNewsKeyword.add(str);
                }
            }else { // 존재함 -> 봄
                seeNewsSeq.add(news.getNewSeq());
                mfRecommendResponseList.add(new MFRecommendResponse(user.getUserSeq(), news.getNewSeq(), news.getSimilarity(), rowWeight));
                for(String str : news.getKeyword()) {
                    seenNewsKeyword.add(str);
                }
            }
        }

        // 이미 봤던 뉴스
        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.add(rowWeight))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(seenNewsKeyword))
                .execute();

        // 한번도 안본 뉴스
        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.add(highWeight))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(notSeenNewsKeyword))
                .execute();

        // fastApi로 전송
        fastApiService.updateWeigth(user, mfRecommendResponseList);

    }

    @Override
    @Transactional
    public void incrementKeywordWeight(User user, List<String> list, double weight) {

        List<UserKeyword> insertKeyword = new ArrayList<>();
        List<String> updateKeyword = new ArrayList<>();

        for (String keyword : list) {
            boolean exists = queryFactory.selectOne()
                    .from(userKeyword)
                    .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.eq(keyword))
                    .fetchFirst() != null;

            if (!exists) {
                UserKeyword newKeyword = new UserKeyword(user, keyword, weight);
                insertKeyword.add(newKeyword);
            }else {
                updateKeyword.add(keyword);
            }
        }

        System.out.println(insertKeyword);
        saveAll(insertKeyword);

        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.add(weight))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(list))
                .execute();

    }

    @Transactional
    public void saveAll(List<UserKeyword> list) {
        String sql = "INSERT INTO user_keyword (user_seq, keyword, weight) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                list,
                list.size(),
                (PreparedStatement ps, UserKeyword userKeyword) -> {
                    ps.setInt(1, userKeyword.getUser().getUserSeq());
                    ps.setString(2, userKeyword.getKeyword().getKeyword());
                    ps.setDouble(3, userKeyword.getWeight());
                });
    }

}
