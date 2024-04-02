package com.ssafy.seodangdogbe.keyword.service;

import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.dto.*;
import com.ssafy.seodangdogbe.keyword.repository.JoinKeywordRepository;
import com.ssafy.seodangdogbe.keyword.repository.UserKeywordRepository;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    public final UserKeywordRepository userKeywordRepository;

    private final JoinKeywordRepository joinKeywordRepository;


    // 새로고침 시 뉴스 키워드 감소
    public MessageResponseDto minusKeywordListWeight(User user, List<NewsRefreshReqDto> newsRefreshReqDtoList, double highWeight, double rowWeight){
        return userKeywordRepository.decrementKeywordWeight(user, newsRefreshReqDtoList, highWeight, rowWeight);
    }

    // 새로고침 시 뉴스 키워드 감소
    @Transactional
    public MessageResponseDto minusKeywordListWeightV2(User user, List<DeWeightReqDto> deWeightReqDtoList){
        return userKeywordRepository.decrementKeywordWeightV2(user, deWeightReqDtoList);
    }

    // 클릭 시 해당 뉴스 키워드 증가 - map
    @Transactional
    public void addKeywordMapWeight(User user, Map<String, Double> newsKeywordMap, double weight){
        userKeywordRepository.incrementClickedKeywordMapWeight(user, newsKeywordMap, weight);
    }

    @Transactional
    public void addKeywordListWeight(User user, List<String> newsKeywordList, double weight){
        userKeywordRepository.incrementClickedKeywordWeight(user, newsKeywordList, weight);
    }

    public List<JoinKeywordDto> findAllKeywords() {
        return joinKeywordRepository.findAllKeywords().stream()
                .map(entity -> new JoinKeywordDto(entity.getJoinKeywordSeq(), entity.getKeyword()))
                .collect(Collectors.toList());
    }

    public List<UserKeywordDto> getWordCloudKeywords(User user) {
        return userKeywordRepository.getWordCloudUserKeyword(user).stream()
                .map(entity -> new UserKeywordDto(entity.getKeyword().getKeyword(), entity.getWeight().floatValue()))
                .collect(Collectors.toList());
    }

}
