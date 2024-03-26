package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.dto.BadgeDto;
import com.ssafy.seodangdogbe.user.service.BadgeService;
import com.ssafy.seodangdogbe.user.service.UserBadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    public final UserService userService;
    public final BadgeService badgeService;
    public final UserBadgeService userBadgeService;

    @GetMapping
    public List<BadgeDto> getAllBadgeList(){
        return badgeService.getAllBadgeList();
    }

    @GetMapping("/my-badges")
    public void getMyBadgeList(){
        int userSeq = userService.getUserSeq();
        User user = userService.getUserByUserSeq(userSeq);
        userBadgeService.getUserBadgeList(user);
    }

    @PatchMapping("/represent-badge")
    public void setRepresentBadge(){

    }

}
