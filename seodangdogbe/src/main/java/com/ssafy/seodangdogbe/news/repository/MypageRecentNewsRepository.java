package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageRecentNewsRepository extends JpaRepository<News, Long>, MypageRecentNewsRepositoryCustom{
}
