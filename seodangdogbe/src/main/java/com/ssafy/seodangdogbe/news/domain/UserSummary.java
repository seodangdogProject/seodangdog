package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.common.JsonConverter;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Getter
//@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserSummary {
    @Convert(converter = JsonConverter.class)
    @Transient
    private String userSummaryContent;

    @Convert(converter = JsonConverter.class)
    @Transient
    private List<String> userSummaryKeyword;
}
