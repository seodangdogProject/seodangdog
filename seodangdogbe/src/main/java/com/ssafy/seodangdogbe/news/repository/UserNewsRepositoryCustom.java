package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface UserNewsRepositoryCustom {
    List<LocalDateTime> findSolvedUserNews(User user, LocalDateTime start, LocalDateTime end);

    UserNews findRecentViewUserNews(User user);
    UserNews findRecentSolvedUserNews(User user);
}
