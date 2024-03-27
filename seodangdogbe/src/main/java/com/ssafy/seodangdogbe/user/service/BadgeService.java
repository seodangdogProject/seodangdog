package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.user.repository.BadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BadgeService {
    public final BadgeRepository badgeRepository;


}
