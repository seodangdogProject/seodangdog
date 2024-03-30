package com.ssafy.seodangdogbe.keyword.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {
    @Id
    @Column(length = 20)
    private String keyword;

    @Override
    public String toString() {
        return "Keyword{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}
