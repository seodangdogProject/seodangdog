package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.user.service.BadgeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    public final BadgeService badgeService;

    @GetMapping
    public void getBadgeList(){

    }

    @GetMapping("/mybadges")
    public void getMyBadgeList(){

    }

    @PatchMapping("/represent-badge")
    public void setRepresentBadge(){

    }

}
