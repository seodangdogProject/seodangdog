package com.ssafy.seodangdogbe.keyword.repository;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshReqDto;
import com.ssafy.seodangdogbe.user.domain.User;

import java.util.*;

public interface UserKeywordRepositoryCustom {
    void decrementKeywordWeight(User user, List<NewsRefreshReqDto> dto, double highWeight, double rowWeight);

    void incrementKeywordWeight(User user, List<String> list, double weight);
}
