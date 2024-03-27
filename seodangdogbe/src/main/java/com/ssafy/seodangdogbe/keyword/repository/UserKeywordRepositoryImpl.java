package com.ssafy.seodangdogbe.keyword.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.seodangdogbe.keyword.domain.QUserKeyword.userKeyword;

@Repository
@RequiredArgsConstructor
public class UserKeywordRepositoryImpl implements UserKeywordRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;

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
