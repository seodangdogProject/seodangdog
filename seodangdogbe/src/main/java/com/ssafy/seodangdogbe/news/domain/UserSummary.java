package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.common.JsonConverter;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Embeddable;
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
    private String userSummaryContent;

    @Convert(converter = JsonConverter.class)
    private List<String> userSummaryKeyword;
}
