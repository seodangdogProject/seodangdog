package com.ssafy.seodangdogbe.keyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.keyword.dto.*;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.bson.LazyBSONList;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public MessageResponseDto decrementKeywordWeight(User user, List<NewsRefreshReqDto> newsRefreshReqDtoList, double highWeight, double rowWeight) {
        List<Long> seeNewsSeq = new ArrayList<>();
        List<Long> notSeenNewsSeq = new ArrayList<>();
        loseWeightFastReqDto dto = new loseWeightFastReqDto();
        List<InfoDto> infoDtos = new ArrayList<>();

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
                infoDtos.add(new InfoDto(news.getNewSeq(), highWeight));
                for(String str : news.getKeyword()) {
                    notSeenNewsKeyword.add(str);
                }
            }else { // 존재함 -> 봄
                seeNewsSeq.add(news.getNewSeq());
                infoDtos.add(new InfoDto(news.getNewSeq(), rowWeight));
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

        // fast api
        dto.setUserSeq(user.getUserSeq());
        dto.setInfo(infoDtos);

        // fastApi로 전송
        return fastApiService.updateWeigth(dto);
    }

    @Override
    @Transactional
    public MessageResponseDto decrementKeywordWeightV2(User user, List<DeWeightReqDto> deWeightReqDtoList) {

        List<DeWeightReqDto.KeywordInfo> list = DeWeightReqDto.extractKeywordInfoList(deWeightReqDtoList);
        loseWeightFastReqDto dto = new loseWeightFastReqDto();

        // 가중치 낮추기
        updateAll(user, list);

        // fast Api로 전송하기
        dto.setUserSeq(user.getUserSeq());
        dto.setInfo(DeWeightReqDto.extractInfoDtoList(deWeightReqDtoList));

        // fastApi로 전송
        return fastApiService.updateWeigth(dto);
    }

    @Override
    @Transactional
    public void incrementKeywordWeight(User user, List<String> newskeywordList, double weight) {
        List<UserKeyword> insertKeyword = new ArrayList<>();
        List<String> updateKeyword = new ArrayList<>();

        for (String keyword : newskeywordList) {
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

        System.out.println("새로운 키워드 : "+insertKeyword);
        saveAll(insertKeyword);

        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.add(weight))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(newskeywordList))
                .execute();

    }


    @Transactional
    public void saveAll(List<UserKeyword> list) {
        String sql = "INSERT INTO user_keyword (user_seq, keyword, weight) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement sql, int i) throws SQLException {
                        sql.setInt(1, list.get(i).getUser().getUserSeq());
                        sql.setString(2, list.get(i).getKeyword().getKeyword());
                        sql.setDouble(3, list.get(i).getWeight());
                    }

                    @Override
                    public int getBatchSize() {
                        return 10;
                    }
                });
    }

    @Transactional
    public void updateAll(User user, List<DeWeightReqDto.KeywordInfo> list) {
        String sql = "UPDATE user_keyword SET weight = weight / 2 " +
                "WHERE user_seq =? AND keyword = ?";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement sql, int i) throws SQLException {
//                        sql.setDouble(1, list.get(i).getWeight());
                        sql.setInt(1, user.getUserSeq());
                        sql.setString(2, list.get(i).getKeyword());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });
    }


    @Override
    @Transactional
    public List<UserKeyword> getWordCloudUserKeyword(User user) {
        return queryFactory.selectFrom(userKeyword)
                .where(userKeyword.user.eq(user))
                .limit(400)
                .fetch();
    }
}
