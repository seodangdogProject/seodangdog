package com.ssafy.seodangdogbe.media.domain;

import com.ssafy.seodangdogbe.media.domain.Media;
import com.ssafy.seodangdogbe.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserMedia {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMediaSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_code")
    private Media media;

    private int weight;
}
