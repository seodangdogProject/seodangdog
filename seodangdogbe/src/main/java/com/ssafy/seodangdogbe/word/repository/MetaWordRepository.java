package com.ssafy.seodangdogbe.word.repository;

import com.ssafy.seodangdogbe.word.domain.MetaWord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MetaWordRepository extends MongoRepository<MetaWord, String> {

    Optional<MetaWord> findByWord(String word);

    boolean existsByWord(String word);
}
