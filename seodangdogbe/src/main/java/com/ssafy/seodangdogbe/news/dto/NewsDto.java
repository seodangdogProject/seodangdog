package com.ssafy.seodangdogbe.news.dto;

import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "meta_news")
public class NewsDto {
    @Id
    private ObjectId id; // MongoDB의 _id
    private String newsTitle;
    private List<String> newsSummary;
    private String newsCreatedAt;
    private String newsReporter;
    private String newsImgUrl;
    private String newsMainText;
    private String newsUrl;
    private MediaDto media;

    @Data
    public static class MediaDto {
        private String mediaCode;
        private String mediaName;
    }
}
