package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;
import com.ssafy.seodangdogbe.user.repository.UserBadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserBadgeService {

    public final UserBadgeRepository userBadgeRepository;

    // 사용자 보유 뱃지 이름 조회
    public List<String> getUserBadgeList(User user) {
        List<String> badges = new ArrayList<>();
        List<UserBadge> findBadges = userBadgeRepository.findAllByUser(user);
        for (UserBadge findBadge : findBadges){
            badges.add(findBadge.getBadge().getBadgeName());
        }
        return badges;
    }

    // 사용자 대표뱃지 이미지 조회
    public String getBadgeImgUrl(User user){
        return user.getBadge().getBadgeImgUrl();
    }
}
