package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRecommendRepository extends JpaRepository <News, Long>, NewsRecommendRepositoryCustom{
}
