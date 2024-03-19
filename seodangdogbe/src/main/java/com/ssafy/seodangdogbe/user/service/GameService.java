package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.user.domain.UserWord;
import com.ssafy.seodangdogbe.user.dto.GameActivatedResponseDto;
import com.ssafy.seodangdogbe.user.dto.GameGetProblemResponseDto;
import com.ssafy.seodangdogbe.user.dto.GameResultRequestDto;
import com.ssafy.seodangdogbe.user.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final WordMeanService wordMeanService; // 예시로 추가된 서비스, MongoDB에서 뜻을 조회

    @Autowired
    public GameService(GameRepository gameRepository, WordMeanService wordMeanService) {
        this.gameRepository = gameRepository;
        this.wordMeanService = wordMeanService;
    }

    public GameActivatedResponseDto checkGameActivation(int userSeq) {
        long wordCount = gameRepository.countUserWords(userSeq);
        if (wordCount < 10) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "단어 게임 비활성화: 단어 개수가 부족합니다.");
        }
        return new GameActivatedResponseDto(true);
    }

    public GameGetProblemResponseDto getProblems(int userSeq) {
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
        int userSeq = requestDto.getUserSeq();
        List<Long> wordSeqs = requestDto.getWordList().stream()
                .map(GameResultRequestDto.WordInfo::getWordSeq)
                .collect(Collectors.toList());

        // deleteWordsBySeqsAndUserSeq 메서드를 사용하여, 해당 사용자의 단어만 삭제합니다.
        gameRepository.deleteWordsBySeqsAndUserSeq(wordSeqs, userSeq);
    }

}
