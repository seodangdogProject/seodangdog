package com.ssafy.seodangdogbe.keyword.repository;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long>, UserKeywordRepositoryCustom {

    Optional<UserKeyword> findByKeyword(Keyword keyword);

    @Override
    UserKeyword save(UserKeyword userKeyword);

    List<UserKeyword> findAllByUser(User user);

    Optional<UserKeyword> findByUserAndKeyword(User user, Keyword keyword);
}
