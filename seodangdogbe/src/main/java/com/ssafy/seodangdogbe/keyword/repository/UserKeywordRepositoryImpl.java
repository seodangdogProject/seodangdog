package com.ssafy.seodangdogbe.keyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.keyword.dto.*;
import com.ssafy.seodangdogbe.news.service.FastApiService;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ssafy.seodangdogbe.keyword.domain.QUserKeyword.userKeyword;
import static com.ssafy.seodangdogbe.news.domain.QUserNews.userNews;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserKeywordRepositoryImpl implements UserKeywordRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;
    private final FastApiService fastApiService;

    @Override
    @Transactional
    public MessageResponseDto decrementKeywordWeight(User user, List<NewsRefreshReqDto> newsRefreshReqDtoList, double highWeight, double rowWeight) {
        List<Long> seeNewsSeq = new ArrayList<>();
        List<Long> notSeenNewsSeq = new ArrayList<>();
        updateWeightFastReqDto dto = new updateWeightFastReqDto();
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

        System.out.println(deWeightReqDtoList);
        List<DeWeightReqDto.KeywordInfo> list = DeWeightReqDto.extractKeywordInfoList(deWeightReqDtoList);
        updateWeightFastReqDto dto = new updateWeightFastReqDto();

        // 가중치 낮추기
        updateAll(user, list);

        // fast Api로 전송하기
        dto.setUserSeq(user.getUserSeq());
        dto.setInfo(DeWeightReqDto.extractInfoDtoList(deWeightReqDtoList));
        System.out.println(dto);

        // fastApi로 전송
        log.info("fast api 요청 dto " , dto);
        return fastApiService.updateWeigth(dto);
    }

    @Override
    @Transactional
    public void incrementSolvedKeywordWeight(User user, List<String> newskeywordList, double weight) {
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

        System.out.println("새로운 키워드 : " + insertKeyword);
        saveAll(user, insertKeyword);

        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.add(weight))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(newskeywordList))
                .execute();

    }

    // 클릭한 뉴스에 대해서 Map<String, Double> 가중치 추가
    @Override
    @Transactional
    public void incrementClickedKeywordMapWeight(User user, Map<String,Double> newsKeywordList, double weight) {
        List<UserKeyword> insertKeyword = new ArrayList<>();
        List<String> updateKeyword = new ArrayList<>();

        for (String keyword : newsKeywordList.keySet()) {
            boolean exists = queryFactory.selectOne()
                    .from(userKeyword)
                    .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.eq(keyword))
                    .fetchFirst() != null;

            if (!exists) {
                UserKeyword newKeyword = new UserKeyword(user, keyword, newsKeywordList.get(keyword) * weight);
                insertKeyword.add(newKeyword);
            }else {
                updateKeyword.add(keyword);
            }
        }

        // 없으면
        // newsKeywordList.get(keyword)  (몽고디비의 뉴스 서머리 키워드의 가중치) * weight(1.5)

        // 있으면
        // 기존의 mysql에 저장된 가중치 + 3 * 몽고db가중치 = mysql 키워드 가중치

        System.out.println("새로운 키워드 : " + insertKeyword);
        saveAll(user, insertKeyword);

        System.out.println("기존에 존재하는 키워드 : " + updateKeyword);
        updateMultiAll(user, updateKeyword, newsKeywordList, 3);
    }



    // 사용 안함 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    @Transactional
    public void incrementClickedKeywordWeight(User user, List<String> newskeywordList, double weight) {
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


        System.out.println("새로운 키워드 : " + insertKeyword);
        saveAll(user, insertKeyword);

        // 소수점 10째자리까지 만들어주기 위해서 *e10/e10
        queryFactory
                .update(userKeyword)
                .set(userKeyword.weight, userKeyword.weight.multiply(weight).multiply(1e10).divide(1e10))
                .where(userKeyword.user.eq(user), userKeyword.keyword.keyword.in(newskeywordList))
                .execute();
    }

    @Transactional
    public void saveAll(User user, List<UserKeyword> list) {
        // 새로운 키워드 유입 시, 가중치 소수점 10째자리까지 반올림해서 넣기
        String sql = "INSERT INTO user_keyword (user_seq, keyword, weight) " +
                "VALUES (?, ?, ROUND(?,10))";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement sql, int i) throws SQLException {
                        sql.setInt(1, user.getUserSeq());
                        sql.setString(2, list.get(i).getKeyword().getKeyword());
                        sql.setDouble(3, list.get(i).getWeight());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });
    }


    // 새로고침 -> 나누기 2, 소수점 10번째 자리
    @Transactional
    public void updateAll(User user, List<DeWeightReqDto.KeywordInfo> list) {
        String sql = "UPDATE user_keyword SET weight = ROUND(weight / 4, 10) " +
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


    @Transactional
    public void updateMultiAll(User user, List<String> list, Map<String, Double> map, double mult) {
        // 있으면
        // 기존의 mysql에 저장된 가중치 + 3 * 몽고db가중치 = mysql 키워드 가중치

        String sql = "UPDATE user_keyword SET weight = ROUND( weight + ? * ?, 10) " +
                "WHERE user_seq =? AND keyword = ?";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement sql, int i) throws SQLException {
                        sql.setDouble(1, mult);
                        sql.setDouble(2, map.get(list.get(i)));
                        sql.setInt(3, user.getUserSeq());
                        sql.setString(4, list.get(i));
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
                .orderBy(userKeyword.weight.desc())
                .limit(50)
                .fetch();
    }
}
