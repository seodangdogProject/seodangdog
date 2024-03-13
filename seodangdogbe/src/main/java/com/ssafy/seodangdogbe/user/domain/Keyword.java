package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Keyword {

    @Id
    private String keyword;
}
