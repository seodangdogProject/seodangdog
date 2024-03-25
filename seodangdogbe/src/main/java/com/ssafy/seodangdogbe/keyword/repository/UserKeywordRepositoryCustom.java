package com.ssafy.seodangdogbe.keyword.repository;
import com.ssafy.seodangdogbe.user.domain.User;

import java.util.*;

public interface UserKeywordRepositoryCustom {
//    void decrementKeywordWeightByNewsSeq(int userSeq, Long newsSeq);

    void incrementKeywordWeight(User user, List<String> list, double weight);
}
