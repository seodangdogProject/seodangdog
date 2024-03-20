package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.media.domain.Media;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "meta_news") //mongodb collection명 (meta_news)
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaNews {

    @Id
    private String id;

    private String newsTitle;
    private List<String> newsSummary;
    private String newsCreatedAt;

    private String newsReporter;
    private String newsImgUrl;
    private String newsMainText;    // 형태소 별로 나누어서 저장해놓기로 했지 않나?

    @Field("news_url")
    private String newsUrl;

    private Media media;    // media code, name

//    private List<Keyword> newsKeyword;

//    @ElementCollection
//    private List<Quiz> newsQuiz;    // 퀴즈 1,2,3

    @Override
    public String toString() {
        return "MetaNews{" +
                "id='" + id + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsSummary=" + newsSummary +
                ", newsCreatedAt='" + newsCreatedAt + '\'' +
                ", newsReporter='" + newsReporter + '\'' +
                ", newsImgUrl='" + newsImgUrl + '\'' +
                ", newsMainText='" + newsMainText + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", media=" + media +
                '}';
    }
}
