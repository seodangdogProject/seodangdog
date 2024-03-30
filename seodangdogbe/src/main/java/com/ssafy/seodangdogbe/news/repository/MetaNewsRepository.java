package com.ssafy.seodangdogbe.news.repository;

import com.ssafy.seodangdogbe.news.domain.MetaNews;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MetaNewsRepository extends MongoRepository<MetaNews, String> {

}
