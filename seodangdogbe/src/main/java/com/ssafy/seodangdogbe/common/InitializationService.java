package com.ssafy.seodangdogbe.common;

import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.repository.BadgeRepository;
import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.WordItem;
import com.ssafy.seodangdogbe.word.repository.MetaWordRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Bag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializationService {

//    public final EntityManager em;

    public final UserRepository userRepository;
    public final MetaWordRepository metaWordRepository;
    public final BadgeRepository badgeRepository;

    @PostConstruct
    public void init() {
        // 이 메서드는 빈이 초기화된 후에 호출됩니다. 초기화에 필요한 작업을 수행합니다. 사용자 등록 등의 작업을 수행할 수 있습니다.
//        User user1 = new User("jaewon", "1234");
//        User user2 = new User("ssafy", "1234");
//
//        userRepository.save(user1);
//        userRepository.save(user2);

        List<String> badgeImgUrls = new ArrayList<>();
        String imgUrl = "https://seodangdog-s3.s3.ap-northeast-2.amazonaws.com/badges/badge";
        for (int i = 1; i <= 7; i++){
            badgeImgUrls.add(imgUrl+i+".png");
        }

        List<Badge> badges = new ArrayList<>();

        // 뱃지 목록 입력
        badges.add(new Badge("까막눈",badgeImgUrls.get(0),"회원가입 시 주어지는 뱃지입니다.",0));
        badges.add(new Badge("어휘왕",badgeImgUrls.get(1),"어휘 문제를 10개 이상 맞힐 경우 뱃지 획득",10));
        badges.add(new Badge("추론왕",badgeImgUrls.get(2),"추론 문제를 10개 이상 맞힐 경우 뱃지 획득",10));
        badges.add(new Badge("판단왕",badgeImgUrls.get(3),"판단 문제를 10개 이상 맞힐 경우 뱃지 획득",10));
        badges.add(new Badge("요약왕",badgeImgUrls.get(4),"요약까지 완료한 뉴스 갯수가 10개 이상일 경우 뱃지 획득",10));
        badges.add(new Badge("뉴스왕",badgeImgUrls.get(5),"뉴스를 20개 이상 읽을 경우 뱃지 획득",20));
        badges.add(new Badge("퀴즈왕",badgeImgUrls.get(6),"단어 게임에서 단어를 20개 이상 맞힐 경우 뱃지 획득",20));

//        badgeRepository.saveAll(badges);


        // MetaWord 예시 데이터 생성
//        WordItem wordItem1 = new WordItem(1, "품사1", "뜻1", "링크1");
//        WordItem wordItem2 = new WordItem(2, "품사2", "뜻2", "링크2");
//
//        MetaWord word = new MetaWord("단어", "kor", 2, List.of(wordItem1, wordItem2));
//
//        // 예시 데이터 저장
//        try {
//            metaWordRepository.save(word);
//        } catch (Exception e) {
//            System.out.println("중복된 단어는 입력할 수 없습니다.");
//        }

    }
}
