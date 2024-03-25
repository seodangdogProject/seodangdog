package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.UserNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNewsRepository extends JpaRepository<UserNews, Long> {
    @Override
    UserNews save(UserNews userNews);

    Optional<UserNews> findByUserUserSeqAndNewsNewsSeq(int userSeq, Long newsSeq);

}
