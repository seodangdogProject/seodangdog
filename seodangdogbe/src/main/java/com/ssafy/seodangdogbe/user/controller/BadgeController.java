package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageAlterResponseDto;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.dto.BadgeDto;
import com.ssafy.seodangdogbe.user.dto.UserBadgeDto;
import com.ssafy.seodangdogbe.user.service.BadgeService;
import com.ssafy.seodangdogbe.user.service.UserBadgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    public final UserService userService;
    public final BadgeService badgeService;
    public final UserBadgeService userBadgeService;

    @Operation(description = "전체 뱃지 목록 조회")
    @GetMapping
    public List<BadgeDto> getAllBadgeList(){
        return badgeService.getAllBadgeList();
    }

    @Operation(description = "사용자 뱃지 목록 조회")
    @GetMapping("/user")
    public List<BadgeDto> getMyBadgeList(){
        User user = userService.getUser();

        return userBadgeService.getUserBadgeList(user);
    }

    @Operation(description = "사용자 대표 뱃지 변경")
    @PatchMapping("/rep-badge")
    public ResponseEntity<MessageAlterResponseDto> setRepresentBadge(@RequestParam int badgeSeq){
        User user = userService.getUser();

        if (userBadgeService.setRepresentBadge(user, badgeSeq)){
            return ResponseEntity.ok().body(new MessageAlterResponseDto("대표 뱃지 변경에 성공"));
        } else {
            return ResponseEntity.badRequest().body(new MessageAlterResponseDto("대표 뱃지 변경 실패"));
        }
    }

    @Operation(description = "전체 뱃지 목록 + 사용자 뱃지 현황")
    @GetMapping("/info")
    public List<UserBadgeDto>  getBadgeInfoAndUserExp(){
        User user = userService.getUser();

        return userBadgeService.getBadgeInfoAndUserExp(user);
    }

}
