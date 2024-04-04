package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import com.ssafy.seodangdogbe.word.dto.UserWordDto;
import com.ssafy.seodangdogbe.word.repository.UserWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWordService {

    public final UserService userService;
    public final UserWordRepository userWordRepository;

    // 사용자단어 테이블에서 찾아서 삭제여부와 함께 반환
    public UserWordDto findUserWord(int userSeq, String word) {
        Optional<UserWord> findUserWord = userWordRepository.findByUserUserSeqAndWord(userSeq, word);

        return findUserWord.map(UserWordDto::new).orElse(null);
    }

    // 사용자단어 테이블에 userWord 저장
    public UserWordDto setUserWord(User user, String word) {
        UserWord userWord = userWordRepository.save(new UserWord(user, word));
        return new UserWordDto(userWord);
    }

    // 사용자단어 테이블에 삭제된 단어를 update
    public void updateUserWordExist(int userSeq, String word) {
        Optional<UserWord> findUserWord = userWordRepository.findByUserUserSeqAndWord(userSeq, word);
        findUserWord.ifPresent(userWord -> userWord.setIsDelete(false));
        System.out.println("사용자 단어 복구 성공");
    }

}