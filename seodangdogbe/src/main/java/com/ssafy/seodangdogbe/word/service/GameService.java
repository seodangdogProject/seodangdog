package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import com.ssafy.seodangdogbe.word.dto.GameActivatedResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameGetProblemResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameResultRequestDto;
import com.ssafy.seodangdogbe.word.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final WordMeanService wordMeanService; // 예시로 추가된 서비스, MongoDB에서 뜻을 조회
    private UserService userService;
    @Autowired
    public GameService(GameRepository gameRepository, WordMeanService wordMeanService, UserService userService) {
        this.gameRepository = gameRepository;
        this.wordMeanService = wordMeanService;
        this.userService = userService;
    }

    public GameActivatedResponseDto checkGameActivation() {
        int userSeq = userService.getUserSeq();
        long wordCount = gameRepository.countUserWords(userSeq);
        if (wordCount < 10) {
            // 게임 비활성화: 단어 개수가 부족하여 GameActivatedResponseDto에 isActivated를 false로 설정
            return new GameActivatedResponseDto(false);
        }
        // 단어 개수가 10개 이상일 경우, 게임을 활성화합니다.
        return new GameActivatedResponseDto(true);
    }


    public GameGetProblemResponseDto getProblems() {
        int userSeq = userService.getUserSeq();
        List<UserWord> randomWords = gameRepository.findRandomWordsByUserSeq(userSeq, 10);
        List<GameGetProblemResponseDto.WordInfo> wordInfos = randomWords.stream()
                .map(word -> new GameGetProblemResponseDto.WordInfo(
                        word.getWordSeq(),
                        word.getWord(),
                        wordMeanService.findMeanByWord(word.getWord()) // MongoDB에서 단어의 뜻 조회
                ))
                .collect(Collectors.toList());

        return new GameGetProblemResponseDto(wordInfos);
    }

    @Transactional
    public void deleteWords(GameResultRequestDto requestDto) {
        // requestDto에서 userSeq와 wordList를 추출합니다.
        int userSeq = userService.getUserSeq();
        List<Long> wordSeqs = requestDto.getWordList().stream()
                .map(GameResultRequestDto.WordInfo::getWordSeq)
                .collect(Collectors.toList());

        // deleteWordsBySeqsAndUserSeq 메서드를 사용하여, 해당 사용자의 단어만 삭제합니다.
        gameRepository.deleteWordsBySeqsAndUserSeq(wordSeqs, userSeq);
    }

}
