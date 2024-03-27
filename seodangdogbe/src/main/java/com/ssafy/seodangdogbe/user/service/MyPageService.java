package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.news.dto.NewsPreviewListDto;
import com.ssafy.seodangdogbe.news.repository.UserNewsRepository;
import com.ssafy.seodangdogbe.news.repository.UserNewsRepositoryCustom;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.repository.BadgeRepository;
import com.ssafy.seodangdogbe.user.repository.UserBadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
    public final UserRepository userRepository;
    public final BadgeRepository badgeRepository;
    public final UserBadgeRepository userBadgeRepository;

    public final UserNewsRepository userNewsRepository;
    public final UserNewsRepositoryCustom userNewsRepositoryCustom;

    // 출석일 수 조회
    public Integer getAttendanceCount(User user){
         return userNewsRepositoryCustom.countSolvedDate(user);
    }

    // 뉴스를 푼 날짜 목록 조회
    public List<Integer> getSolvedDateRecord(User user) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime start = current.minusDays(100);
        // 지금으로부터 100일 이내의 푼 내역을 순서대로 가져온다.
        List<LocalDateTime> solvedDateList = userNewsRepositoryCustom.findSolvedDateList(user, start, current);

        // 크기 100의 boolean 배열로 반환
        Integer[] streaks = new Integer[100];
        Arrays.fill(streaks, 0);

        for (LocalDateTime solvedDate : solvedDateList){
            int idx = (int) ChronoUnit.DAYS.between(start, solvedDate);
            streaks[idx]++;
        }

        return Arrays.stream(streaks).toList();
    }

    // 사용자가 가장 최근에 본 뉴스 조회
    public NewsPreviewListDto getRecentViewNews(User user){
        UserNews findRecentViewNews = userNewsRepositoryCustom.findRecentViewUserNews(user);
        return new NewsPreviewListDto(findRecentViewNews.getNews());
    }

    // 사용자가 가장 최근에 푼 뉴스 조회
    public NewsPreviewListDto getRecentSolvedNews(User user){
        UserNews findRecentSolvedNews = userNewsRepositoryCustom.findRecentSolvedUserNews(user);
        return new NewsPreviewListDto(findRecentSolvedNews.getNews());
    }
}
