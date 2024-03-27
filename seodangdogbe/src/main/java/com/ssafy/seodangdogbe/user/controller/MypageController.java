package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.dto.MypageResponseDto;
import com.ssafy.seodangdogbe.user.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.ssafy.seodangdogbe.user.dto.MypageResponseDto.*;

@RestController
@RequestMapping("/api/mypages")
@RequiredArgsConstructor
public class MypageController {

    public final UserService userService;
    public final MypageService mypageService;

    @GetMapping
    public MypageResponseDto getMypage(){
        MypageResponseDto resultDto = new MypageResponseDto();
        User user = userService.getUserByUserId();

        resultDto.setUserId(user.getUserId());
        resultDto.setNickname(user.getNickname());

        // ability
        resultDto.setAbility(new UserAbilityDto(user.getUserExp()));
        // ability constant
        int attendanceCount = mypageService.getAttendanceCount(user);
        LocalDateTime joinDate = user.getCreatedAt();
        long duringDate = ChronoUnit.DAYS.between(joinDate, LocalDateTime.now());
        resultDto.getAbility().setConstantAbility((float) attendanceCount / duringDate);

        // badge
        resultDto.setBadgeImgUrl(mypageService.getBadgeImgUrl(user));
        resultDto.setUserBadgeNameList(mypageService.getUserBadgeList(user));

        // streak
        resultDto.setStreakList(mypageService.getSolvedDateRecord(user));

        resultDto.setRecentViewNews(mypageService.getRecentViewNews(user));
        resultDto.setRecentSolvedNews(mypageService.getRecentSolvedNews(user));


        return resultDto;
    }

}
