package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserNewsRepositoryCustom {
    List<LocalDateTime> findSolvedDateList(User user, LocalDateTime start, LocalDateTime end);

    Integer countSolvedDate(User user);

    UserNews findRecentViewUserNews(User user);
    UserNews findRecentSolvedUserNews(User user);
}
