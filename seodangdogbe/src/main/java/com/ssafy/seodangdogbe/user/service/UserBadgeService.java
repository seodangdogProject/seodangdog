package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;
import com.ssafy.seodangdogbe.user.domain.UserExp;
import com.ssafy.seodangdogbe.user.dto.BadgeDto;
import com.ssafy.seodangdogbe.user.repository.BadgeRepository;
import com.ssafy.seodangdogbe.user.repository.UserBadgeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserBadgeService {

    public final BadgeRepository badgeRepository;
    public final UserBadgeRepository userBadgeRepository;

    // 사용자 보유 뱃지 목록 조회
    public List<BadgeDto> getUserBadgeList(User user) {
        List<BadgeDto> badges = new ArrayList<>();
        List<UserBadge> findBadges = userBadgeRepository.findAllByUser(user);
        for (UserBadge findBadge : findBadges){
            badges.add(new BadgeDto(findBadge.getBadge()));
        }
        return badges;
    }

    // 사용자 대표뱃지 이미지 조회
    public String getBadgeImgUrl(User user){
        return user.getBadge().getBadgeImgUrl();
    }

    // 대표뱃지 변경
    public boolean setRepresentBadge(User user, int badgeSeq) {
        List<UserBadge> findBadges = userBadgeRepository.findAllByUser(user);
        Badge badge = badgeRepository.findById(badgeSeq).orElseThrow(NullPointerException::new);

        UserBadge findUserBadge = userBadgeRepository.findByUserAndBadge(user, badge);
        if (findBadges.contains(findUserBadge)){
            findUserBadge.getUser().setBadge(badge);
            return true;
        } else {    // 사용자가 갖고있지 않은 뱃지라면
            return false;
        }
    }

    // 새로운 뱃지 획득 여부 체크
    public String checkNewBadge(User user){
        List<UserBadge> newBadgeList = new ArrayList<>();

        List<Badge> badgeList = badgeRepository.findAll();
        List<UserBadge> findUserBadgeList = userBadgeRepository.findAllByUser(user);
        List<Badge> userBadgeList = findUserBadgeList.stream()
                .map(UserBadge::getBadge)
                .toList();

        UserExp userExp = user.getUserExp();
        for (Badge badge : badgeList){
            if (userBadgeList.contains(badge))
                continue;

            int condition = badge.getBadgeCondition();
            int userCondition = switch (badge.getBadgeName()) {
                case "어휘왕" -> userExp.getWordExp();
                case "추론왕" -> userExp.getInferenceExp();
                case "판단왕" -> userExp.getJudgementExp();
                case "요약왕" -> userExp.getSummaryExp();
                case "뉴스왕" -> userExp.getNewsExp();
                case "퀴즈왕" -> userExp.getWordGameExp();
                default -> 0;
            };

            if (condition <= userCondition) {
                newBadgeList.add(new UserBadge(user, badge));
            }
        }

        // 새로 획득한 뱃지 전달을 어떻게 할 지?
        userBadgeRepository.saveAll(newBadgeList);;

        if (!newBadgeList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (UserBadge badgeDto : newBadgeList){
                sb.append("\"");
                sb.append(badgeDto.getBadge().getBadgeName());
                sb.append("\" ");
            }
            sb.append("뱃지를 획득하였습니다.");

            return sb.toString();
        }
        return null;
    }
}
