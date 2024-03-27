package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.dto.WordDto;
import com.ssafy.seodangdogbe.word.repository.MetaWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class WordMeanService {
    private final MetaWordRepository metaWordRepository;

    @Autowired
    public WordMeanService(MetaWordRepository metaWordRepository) {
        this.metaWordRepository = metaWordRepository;
    }

    public WordDto.MetaWordDto findMeanByWord(String word) {
        Optional<MetaWord> metaWordOpt = metaWordRepository.findByWord(word);
        if (metaWordOpt.isPresent()) {
            MetaWord metaWord = metaWordOpt.get();
            return new WordDto.MetaWordDto(metaWord);
        } else {
            return null;
        }
    }
}


