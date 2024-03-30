package com.ssafy.seodangdogbe.user.service;

import com.ssafy.seodangdogbe.user.domain.Badge;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserBadge;
import com.ssafy.seodangdogbe.user.domain.UserExp;
import com.ssafy.seodangdogbe.user.dto.BadgeDto;
import com.ssafy.seodangdogbe.user.dto.UserBadgeDto;
import com.ssafy.seodangdogbe.user.repository.BadgeRepository;
import com.ssafy.seodangdogbe.user.repository.UserBadgeRepository;
import com.ssafy.seodangdogbe.user.repository.UserBadgeRepositoryCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserBadgeService {

    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final UserBadgeRepositoryCustom userBadgeRepositoryCustom;

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
        return userBadgeRepositoryCustom.findByUserRepBadge(user)
                .getBadge().getBadgeImgUrl();

    }

    // 대표뱃지 변경
    public boolean setRepresentBadge(User user, int newBadgeSeq) {
        List<UserBadge> findUserBadges = userBadgeRepository.findAllByUser(user);

        // 현재 대표뱃지 -> 대표뱃지 해제
        UserBadge curRep = userBadgeRepositoryCustom.findByUserRepBadge(user);
        curRep.setRepBadge(false);

        // 새로운 대표뱃지 설정
        UserBadge newRep = userBadgeRepositoryCustom.findUserBadge(user, newBadgeSeq);
        if (newRep != null){
            newRep.setRepBadge(true);
            return true;
        }

        return false;
//        UserBadge newRep = null;
//                .orElseThrow(() -> new NullPointerException("새로 지정할 대표 뱃지를 찾을 수 없습니다."));
//        newRep.setRepBadge(true);
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
            if (userBadgeList.contains(badge)) continue;

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

    // 전체뱃지 정보 + 사용자경험치 정보
    public List<UserBadgeDto> getBadgeInfoAndUserExp(User user) {
        List<UserBadge> findUserBadgeList = userBadgeRepositoryCustom.findAllByUser(user);
        List<Badge> findBadgeList = findUserBadgeList.stream().map(UserBadge::getBadge).toList();
        List<UserBadgeDto> result = new ArrayList<>(findUserBadgeList.stream().map(UserBadgeDto::new).toList());

        List<Badge> badgeList = badgeRepository.findAll();

        for (Badge badge : badgeList){
            if (!findBadgeList.contains(badge)){    // 보유하지 않은 뱃지
                result.add(new UserBadgeDto(user, badge));
            }
        }

        return result.stream().sorted(((o1, o2) -> o1.getBadgeDto().getBadgeSeq() - o2.getBadgeDto().getBadgeSeq())).toList();
    }
}
