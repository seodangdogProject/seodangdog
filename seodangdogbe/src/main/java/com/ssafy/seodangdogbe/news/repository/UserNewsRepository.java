package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.UserNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNewsRepository extends JpaRepository<UserNews, Long> {
    @Override
    UserNews save(UserNews userNews);

    UserNews findByUserUserSeqAndNewsNewsSeq(int userSeq, Long newsSeq);

//    UserNews findByUserSeqAndNewsSeq(int userSeq, Long newsSeq);
}
