package com.ssafy.seodangdogbe.news.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.seodangdogbe.auth.service.UserService;
import com.ssafy.seodangdogbe.common.MessageResponseDto;
import com.ssafy.seodangdogbe.keyword.dto.loseWeightFastReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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

        Mono<List<CbfRecommendResponse>> list =
                this.webClient.get()
                .uri("/fast/cbf_recom/{userSeq}", userSeq)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("API 호출 실패, 상태 코드: " + clientResponse.statusCode())))
                .bodyToFlux(CbfRecommendResponse.class)
                        .collectList();

        System.out.println(list.blockOptional().get());
        return list;
    }

    public MessageResponseDto updateWeigth(loseWeightFastReqDto loseWeightFastReqDtoList) {
        return this.webClient.post()
                .uri("/fast/mf_recom/update")
                .bodyValue(loseWeightFastReqDtoList)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("API 호출 실패, 상태 코드: " + clientResponse.statusCode())))
                .bodyToMono(MessageResponseDto.class)// 응답값이 뭔지는 왜 안알ㄹ줬노... -> 일단 string
                .block(); // 동기적 처리로 일단 냅두기

    }

    public Mono<List<MfRecommendResponse>> fetchMfRecommendations() {
        int userSeq = userService.getUserSeq();
        return this.webClient.get()
                .uri("/fast/mf_recom/{userSeq}", userSeq)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("API 호출 실패, 상태 코드: " + clientResponse.statusCode())))
                .bodyToMono(new ParameterizedTypeReference<List<MfRecommendResponse>>() {
                });
    }






    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class MfRecommendResponse {

        @JsonProperty("news_seq")
        private Long newsSeq;

        @JsonProperty("news_title")
        private String title;

        @JsonProperty("news_similarity")
        private Double similarity;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CbfRecommendResponse {
        private String news_id;

        private Long news_seq;

        private String news_title;

        private Double news_similarity;
    }
}


