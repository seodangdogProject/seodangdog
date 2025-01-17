package com.ssafy.seodangdogbe.user.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.media.domain.UserMedia;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    // 사용자 경험치
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_exp_seq")
    @Builder.Default
    private UserExp userExp = new UserExp();

//    // 사용자 대표 뱃지
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "badge_seq")
//    private UserBadge userBadge;

    @Column(length = 15, unique = true, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;

    private String role;

    // 양방향 연관관계 매핑 (주인X)
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserWord> userWords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserKeyword> userKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserBadge> userBadges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserMedia> userMediaList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserNews> userNewsList = new ArrayList<>();

    // 함수
    public List<String> getRoleList(){
        if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void addUserBadge(UserBadge userBadge) {
        userBadge.setUser(this);
        this.userBadges.add(userBadge);
    }

//    public User(String userId, String password, String nickname, Badge badge){
//        this.userId = userId;
//        this.password = password;
//        this.nickname = nickname;
//        this.badge = badge;
//        this.role = "USER";
//    }


}

