package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import com.ssafy.seodangdogbe.word.dto.MyWordResponseDto;
import com.ssafy.seodangdogbe.word.repository.MyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyWordService {
    private final MyWordRepository myWordRepository;
    private final WordMeanService wordMeanService;
    private UserService userService;

    @Autowired
    public MyWordService(MyWordRepository myWordRepository,  WordMeanService wordMeanService, UserService userService) {
        this.myWordRepository = myWordRepository;
        this.wordMeanService = wordMeanService;
        this.userService = userService;

    }

    // 사용자의 모든 단어를 조회하는 메서드
    public MyWordResponseDto findAllUserWords() {
        int userSeq = userService.getUserSeq();
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
        int userSeq = userService.getUserSeq();
        return myWordRepository.findById(wordSeq).map(userWord -> {
            // 단어의 소유자가 현재 인증된 사용자인지 확인합니다.
            if (userWord.getUser().getUserSeq() == userSeq) {
                // 소유자가 맞으면 단어를 삭제합니다.
                myWordRepository.deleteById(wordSeq);
                return true; // 삭제 성공
            } else {
                // 소유자가 아니면 삭제하지 않습니다.
                return false; // 소유자 불일치
            }
        }).orElse(false); // 단어가 존재하지 않음
    }
}
