package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.dto.BadgeDto;
import com.ssafy.seodangdogbe.user.repository.BadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeService {

    public final BadgeRepository badgeRepository;

    // 뱃지 전체 목록 조회
    public List<BadgeDto> getAllBadgeList() {
        List<Badge> badgeList = badgeRepository.findAll();
        List<BadgeDto> result = new ArrayList<>();
        for (Badge badge : badgeList){
            result.add(new BadgeDto(badge));
        }
        return result;
    }
}
