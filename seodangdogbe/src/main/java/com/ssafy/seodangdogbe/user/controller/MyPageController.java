package com.ssafy.seodangdogbe.user.controller;

import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.keyword.dto.UserKeywordDto;
import com.ssafy.seodangdogbe.keyword.service.KeywordService;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.dto.MyPageResponseDto;
import com.ssafy.seodangdogbe.user.dto.UserInfoCorrectResponseDto;
import com.ssafy.seodangdogbe.user.dto.UserNicknameModifyReqDto;
import com.ssafy.seodangdogbe.user.service.MyPageService;
import com.ssafy.seodangdogbe.user.service.UserBadgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ssafy.seodangdogbe.user.dto.MyPageResponseDto.*;


@RestController
@RequestMapping("/api/mypages")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final UserService userService;
    private final KeywordService keywordService;
    private final MyPageService mypageService;
    private final UserBadgeService userBadgeService;

    @Operation(description = "마이페이지 조회")
    @GetMapping
    public MyPageResponseDto getMyPage(){
        MyPageResponseDto resultDto = new MyPageResponseDto();
        User user = userService.getUser();

        resultDto.setUserId(user.getUserId());
        resultDto.setNickname(user.getNickname());

        // ability
        resultDto.setAbility(new UserAbilityDto(user));
        // ability constant
        int attendanceCount = mypageService.getAttendanceCount(user);
        long duringDate = ChronoUnit.DAYS.between(user.getCreatedAt(), LocalDateTime.now());
        resultDto.getAbility().setConstantAbility((float) attendanceCount / duringDate);

        // badge
        resultDto.setBadgeImgUrl(userBadgeService.getBadgeImgUrl(user));
        resultDto.setUserBadgeList(userBadgeService.getUserBadgeList(user));

        // streak
        resultDto.setStreakList(mypageService.getSolvedDateRecord(user));

        // recent News
        resultDto.setRecentViewNews(mypageService.getRecentViewNews(user));
        resultDto.setRecentSolvedNews(mypageService.getRecentSolvedNews(user));

        // wordCloud
        List<UserKeywordDto> userKeywords = keywordService.getWordCloudKeywords(user);
        resultDto.setWordCloudKeywords(userKeywords);

        return resultDto;
    }
    
    @Operation(description = "닉네임 변경")
    @PatchMapping("/nickname")
    public ResponseEntity<MessageResponseDto> modifyNickname(@RequestBody UserNicknameModifyReqDto dto){
        MessageResponseDto result;
        try{
            userService.modifyNickname(dto);
            result = new MessageResponseDto("성공");
        }catch (IllegalArgumentException e){
            result = new MessageResponseDto("실패");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(description = "닉네임, 대표뱃지만 조회")
    @GetMapping("/nickname-badge")
    public UserInfoCorrectResponseDto getNicknameAndBadge(){
        User user = userService.getUser();

        return new UserInfoCorrectResponseDto(user.getNickname(), userBadgeService.getBadgeImgUrl(user));
    }
}
