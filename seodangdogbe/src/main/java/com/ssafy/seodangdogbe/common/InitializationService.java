package com.ssafy.seodangdogbe.common;

import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.WordItem;
import com.ssafy.seodangdogbe.word.repository.MetaWordRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializationService {

//    public final EntityManager em;

    public final UserRepository userRepository;
    public final MetaWordRepository metaWordRepository;

    @PostConstruct
    public void init() {
        // 이 메서드는 빈이 초기화된 후에 호출됩니다. 초기화에 필요한 작업을 수행합니다. 사용자 등록 등의 작업을 수행할 수 있습니다.
        User user1 = new User("jaewon", "1234");
        User user2 = new User("ssafy", "1234");

        if (userRepository.findByUserId(user1.getUserId()).isEmpty()){
            userRepository.save(user1);
        }

        if (userRepository.findByUserId(user2.getUserId()).isEmpty()){
            userRepository.save(user2);
        }


        // MetaWord 예시 데이터 생성
        WordItem wordItem1 = new WordItem(1, "품사1", "뜻1", "링크1");
        WordItem wordItem2 = new WordItem(2, "품사2", "뜻2", "링크2");

        MetaWord word = new MetaWord("단어", "kor", 2, List.of(wordItem1, wordItem2));

        // 예시 데이터 저장
        try {
            metaWordRepository.save(word);
        } catch (Exception e) {
            System.out.println("중복된 단어는 입력할 수 없습니다.");
        }

    }
}
