package com.ssafy.seodangdogbe.user.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import com.ssafy.seodangdogbe.keyword.domain.UserKeyword;
import com.ssafy.seodangdogbe.media.domain.UserMedia;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
@Entity
@Getter @Setter
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userSeq;

    // 사용자 경험치
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_exp_seq")
    @Builder.Default
    private UserExp userExp = new UserExp();

    // 사용자 대표 뱃지
    // 1:1 아닌거 아닌가?
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_seq")
    private Badge badge;

    @Column(length = 15, unique = true)
    private String userId;
    private String password;

    // 양방향 연관관계 매핑 (주인X)
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserWord> userWords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserKeyword> userKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserBadge> userBadges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserMedia> userMediaList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserNews> userNewsList = new ArrayList<>();

    private String role;

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

    public User(String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    public User(int userSeq){
        this.userSeq = userSeq;
    }


}

