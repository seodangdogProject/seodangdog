package com.ssafy.seodangdogbe.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitializationService {

    public final EntityManager em;
    public final JPAQueryFactory jpaQueryFactory;

    public final UserRepository userRepository;

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
    }
}
