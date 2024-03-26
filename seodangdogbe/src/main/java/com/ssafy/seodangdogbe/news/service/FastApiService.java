package com.ssafy.seodangdogbe.news.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.keyword.dto.MFRecommendResponse;
import com.ssafy.seodangdogbe.user.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FastApiService {
    private final WebClient webClient;
    private final UserService userService;

    @Autowired
    public FastApiService(WebClient webClient, UserService userService) {

        this.webClient = webClient;
        this.userService = userService;
    }

    public Mono<List<CbfRecommendResponse>> fetchRecommendations() {
        int userSeq = userService.getUserSeq();
        return this.webClient.get()
                .uri("/fast/cbf_recom/{userSeq}", userSeq)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                clientResponse -> Mono.error(new RuntimeException("API 호출 실패, 상태 코드: " + clientResponse.statusCode())))
                .bodyToMono(new ParameterizedTypeReference<List<CbfRecommendResponse>>() {});
    }

    public void updateWeigth(User user, List<MFRecommendResponse> mfRecommendResponseList) {
        this.webClient.post()
                .uri("/fast/mf_recom/update")
                .bodyValue(mfRecommendResponseList)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("API 호출 실패, 상태 코드: " + clientResponse.statusCode())))
                .bodyToMono(String.class)// 응답값이 뭔지는 왜 안알ㄹ줬노... -> 일단 string
                .block(); // 동기적 처리로 일단 냅두기
    }

    @Data
    public class CbfRecommendResponse {
        @JsonProperty("news_id")
        private String id;

        @JsonProperty("news_seq")
        private Long newsSeq;

        @JsonProperty("news_title")
        private String title;

        @JsonProperty("news_similarity")
        private Double similarity;

    }
}
