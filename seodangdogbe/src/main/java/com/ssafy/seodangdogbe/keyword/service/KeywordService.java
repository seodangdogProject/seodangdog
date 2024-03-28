package com.ssafy.seodangdogbe.keyword.service;

import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.domain.JoinKeyword;
import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.keyword.dto.JoinKeywordDto;
import com.ssafy.seodangdogbe.keyword.dto.NewsKeywordDto;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.keyword.dto.UserKeywordDto;
import com.ssafy.seodangdogbe.keyword.repository.JoinKeywordRepository;
import com.ssafy.seodangdogbe.keyword.repository.JoinKeywordRepositoryCustom;
import com.ssafy.seodangdogbe.keyword.repository.UserKeywordRepository;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static com.ssafy.seodangdogbe.keyword.dto.NewsKeywordDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    public final UserKeywordRepository userKeywordRepository;

    private final JoinKeywordRepository joinKeywordRepository;


    // 클릭 시 해당 뉴스 키워드 증가
    public MessageResponseDto minusKeywordListWeight(User user, List<NewsRefreshReqDto> newsRefreshReqDtoList, double highWeight, double rowWeight){
        return userKeywordRepository.decrementKeywordWeight(user, newsRefreshReqDtoList, highWeight, rowWeight);
    }

    public void addKeywordListWeight(User user, List<String> newsKeywordList, double weight){
        userKeywordRepository.incrementKeywordWeight(user, newsKeywordList, weight);
    }

    public List<JoinKeywordDto> findAllKeywords() {
        return joinKeywordRepository.findAllKeywords().stream()
                .map(entity -> new JoinKeywordDto(entity.getJoinKeywordSeq(), entity.getKeyword()))
                .collect(Collectors.toList());
    }

    public List<JoinKeyword> getAllJoinKeywords() {
        return joinKeywordRepository.findAll();
    }

    public List<UserKeywordDto> getAllUserKeywords(){

    }
}
