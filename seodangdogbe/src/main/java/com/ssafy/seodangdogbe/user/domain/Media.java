package com.ssafy.seodangdogbe.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Media {

    @Id
    private String mediaCode;

    private String mediaName;

    private String mediaImgUrl;
}
