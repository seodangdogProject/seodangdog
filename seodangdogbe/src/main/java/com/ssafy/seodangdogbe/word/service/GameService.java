package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import com.ssafy.seodangdogbe.word.dto.GameActivatedResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameGetProblemResponseDto;
import com.ssafy.seodangdogbe.word.dto.GameResultRequestDto;
import com.ssafy.seodangdogbe.word.dto.WordDto;
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
    private final WordMeanService wordMeanService;
    private UserService userService;
    @Autowired
    public GameService(GameRepository gameRepository, WordMeanService wordMeanService, UserService userService) {
        this.gameRepository = gameRepository;
        this.wordMeanService = wordMeanService;
        this.userService = userService;
    }
    private final Map<Integer, List<Long>> activeGameWords = new ConcurrentHashMap<>();
    public GameActivatedResponseDto checkGameActivation() {
        int userSeq = userService.getUserSeq();
        long wordCount = gameRepository.countUserWords(userSeq);
        if (wordCount < 10) {
            // 게임 비활성화: 단어 개수가 부족하여 GameActivatedResponseDto에 isActivated를 false로 설정
            return new GameActivatedResponseDto(false, wordCount);
        }
        // 단어 개수가 10개 이상일 경우, 게임을 활성화합니다.
        return new GameActivatedResponseDto(true, wordCount);
    }


    public GameGetProblemResponseDto getProblems() {
        int userSeq = userService.getUserSeq();
        List<UserWord> randomWords = gameRepository.findRandomWordsByUserSeq(userSeq, 10);
        List<GameGetProblemResponseDto.WordInfo> wordInfos = randomWords.stream()
                .map(word -> {
                    WordDto.MetaWordDto metaWordDto = wordMeanService.findMeanByWord(word.getWord());
                    String definition = metaWordDto != null && !metaWordDto.getItems().isEmpty()
                            ? metaWordDto.getItems().get(0).getDefinition() // 첫 번째 뜻을 사용
                            : "뜻을 찾을 수 없음"; // 뜻이 없는 경우의 기본값 처리
                    return new GameGetProblemResponseDto.WordInfo(
                            word.getWordSeq(),
                            word.getWord(),
                            definition
                    );
                })
                .collect(Collectors.toList());

        return new GameGetProblemResponseDto(wordInfos);
    }

    @Transactional
    public void deleteWords(int userSeq, GameResultRequestDto requestDto) {
//        int userSeq = userService.getUserSeq();
        List<Long> wordSeqs = requestDto.getWordSeq();

        // 사용자 단어게임 경험치 증가
        User user = userService.getUser();
        int exp = user.getUserExp().getWordGameExp();
        user.getUserExp().setWordGameExp(exp + wordSeqs.size());

        // 데이터베이스에서 해당 단어들의 isDelete 상태를 true로 업데이트
        gameRepository.deleteWordsBySeqsAndUserSeq(wordSeqs, userSeq);
    }

}
