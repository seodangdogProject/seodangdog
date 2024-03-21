package com.ssafy.seodangdogbe.news.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FastApiService {
    private final WebClient webClient;

    @Autowired
    public FastApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<CbfRecommendResponse>> fetchRecommendations() {
        return this.webClient.get().uri("/fast/cbf_recom")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CbfRecommendResponse>>() {});
    }

    @Data
    public class CbfRecommendResponse {
        private String id;
        private String title;
        private Double similarity;
    }
}
