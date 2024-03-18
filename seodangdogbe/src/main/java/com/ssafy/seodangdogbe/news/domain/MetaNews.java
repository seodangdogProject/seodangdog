package com.ssafy.seodangdogbe.news.domain;

import com.ssafy.seodangdogbe.keyword.domain.Keyword;
import com.ssafy.seodangdogbe.media.domain.Media;
import jakarta.persistence.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "news") //mongodb collection명 (meta_news)
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaNews {

    @Id
    @Field("_id")
    private String id;

    private String newsTitle;
    private ArrayList<String> newsSummary;
    private String newsCreatedAt;

    private String newsReporter;
    private String newsImgUrl;
    private String newsMainText;
    private String newsUrl;

    private Media media;    // media code, name

//    private List<Keyword> newsKeyword;
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
