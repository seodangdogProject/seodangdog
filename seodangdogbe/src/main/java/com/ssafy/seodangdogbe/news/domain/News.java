package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.common.BaseTimeEntity;
import com.ssafy.seodangdogbe.media.domain.Media;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class News extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsSeq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_code")
    private Media media;

    // mongodb 뉴스 접근 아이디
    @Column(length = 20)
    private String newsAccessId;

    @ColumnDefault("0")
    private int countSolve;
    @ColumnDefault("0")
    private int countView;

    private String newsImgUrl;
    private String newsTitle;
    private String newsDescription;
    private LocalDateTime newsCreatedAt;

    @OneToMany(mappedBy = "news")
    private List<KeywordNews> keywordNewsList = new ArrayList<>();

    @Override
    public String toString() {
        return "News{" +
                "newsSeq=" + newsSeq +
                ", media=" + media +
                ", newsAccessId='" + newsAccessId + '\'' +
                ", countSolve=" + countSolve +
                ", countView=" + countView +
                ", newsImgUrl='" + newsImgUrl + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsDescription='" + newsDescription + '\'' +
                ", newsCreatedAt=" + newsCreatedAt +
                ", keywordNewsList=" + keywordNewsList +
                '}';
    }
}
