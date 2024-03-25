package com.ssafy.seodangdogbe.keyword.service;

import com.ssafy.seodangdogbe.keyword.repository.UserKeywordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    public final UserKeywordRepository userKeywordRepository;

    // 클릭 시 해당 뉴스 키워드 증가
    public void addKeywordListWeight(int userSeq, Long newsSeq, List<String> keywordList){

    }

}
