package com.ssafy.seodangdogbe.media.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Media {

    @Id
    @Column(length = 3)
    private String mediaCode;

    @Column(length = 10)
    private String mediaName;

    private String mediaImgUrl;
}
