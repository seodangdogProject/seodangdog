package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.user.domain.UserWord;
import com.ssafy.seodangdogbe.user.dto.GameGetProblemResponseDto;
import com.ssafy.seodangdogbe.user.dto.MyWordResponseDto;
import com.ssafy.seodangdogbe.user.repository.MyWordRepository;
import com.ssafy.seodangdogbe.user.repository.MyWordRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyWordService {
    private final MyWordRepository myWordRepository;
    private final WordMeanService wordMeanService;

    @Autowired
    public MyWordService(MyWordRepository myWordRepository,  WordMeanService wordMeanService) {
        this.myWordRepository = myWordRepository;
        this.wordMeanService = wordMeanService;

    }

    // 사용자의 모든 단어를 조회하는 메서드
    public MyWordResponseDto findAllUserWords(int userSeq) {
        List<UserWord> userWords = myWordRepository.findAllUserWords(userSeq);
        List<MyWordResponseDto.WordInfo> wordInfos = userWords.stream()
                .map(word -> new MyWordResponseDto.WordInfo(
                        word.getWordSeq(),
                        word.getWord(),
                        wordMeanService.findMeanByWord(word.getWord()) // MongoDB에서 단어의 뜻 조회
                ))
                .collect(Collectors.toList());

        return new MyWordResponseDto(wordInfos);
    }

    // 단어 삭제 메서드
    public boolean deleteWord(Long wordSeq) {
        if (myWordRepository.existsById(wordSeq)) {
            myWordRepository.deleteById(wordSeq);
            return true;
        }
        return false;
    }
}
