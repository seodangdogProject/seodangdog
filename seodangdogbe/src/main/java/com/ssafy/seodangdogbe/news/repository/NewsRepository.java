package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByNewsSeq(Long newsSeq);

}
