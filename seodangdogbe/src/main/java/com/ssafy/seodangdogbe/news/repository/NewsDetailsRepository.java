package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.MetaNews;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NewsDetailsRepository extends MongoRepository<MetaNews, String> {
    @Override
    Optional<MetaNews> findById(String id);
}
