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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
    public final UserRepository userRepository;
    public final BadgeRepository badgeRepository;
    public final UserBadgeRepository userBadgeRepository;

    public final UserNewsRepository userNewsRepository;
    public final UserNewsRepositoryCustom userNewsRepositoryCustom;

//    public final WebClient webClient;

    // 출석일 수 조회
    public Integer getAttendanceCount(User user){
         return userNewsRepositoryCustom.countSolvedDate(user);
    }

    // 뉴스를 푼 날짜 목록 조회
    public Map<LocalDate, Integer> getSolvedDateRecord(User user) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime start = current.minusDays(100);

        // 지금으로부터 100일 이내의 푼 내역을 순서대로 가져온다.
        List<LocalDateTime> solvedDateList = userNewsRepositoryCustom.findSolvedDateList(user, start, current);

        Map<LocalDate, Integer> resultMap = new HashMap<>();
        for (LocalDateTime LDT : solvedDateList){
            LocalDate LD = LDT.toLocalDate();
            resultMap.merge(LD, 1, Integer::sum);
        }

        return resultMap;
    }

    // 사용자가 가장 최근에 본 뉴스 조회
    public NewsPreviewListDto getRecentViewNews(User user){
        UserNews findRecentViewNews = userNewsRepositoryCustom.findRecentViewUserNews(user);
        if (findRecentViewNews == null)
            return null;
        return new NewsPreviewListDto(findRecentViewNews.getNews());
    }

    // 사용자가 가장 최근에 푼 뉴스 조회
    public NewsPreviewListDto getRecentSolvedNews(User user){
        UserNews findRecentSolvedNews = userNewsRepositoryCustom.findRecentSolvedUserNews(user);
        if (findRecentSolvedNews == null)
            return null;
        return new NewsPreviewListDto(findRecentSolvedNews.getNews());
    }

    // 사용자 워드클라우드 fastAPI 조회
//    public Mono<String> getWordCloud(int userSeq){
//        return this.webClient.get()
//                .uri("/fast/mypages/wordclouds/{userSeq}", userSeq)
//                .retrieve()
//                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
//                        clientResponse -> Mono.error(new RuntimeException("API 호출 실패, 상태 코드: " + clientResponse.statusCode())))
//                .bodyToMono(new ParameterizedTypeReference<String>() {
//                });
//    }

}
