package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.repository.BadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {
    public final UserRepository userRepository;
    public final BadgeRepository badgeRepository;

    public String getBadgeImgUrl(User user){
        return user.getBadge().getBadgeImgUrl();
    }

//    public List<String> getBadgeName(User user){
//        List<Badge> userBadges = user.getUserBadges();
//    }
}
