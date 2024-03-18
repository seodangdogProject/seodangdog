package com.ssafy.seodangdogbe.news.Servcie;

import com.ssafy.seodangdogbe.news.Repository.NewsDetailsRepository;
import com.ssafy.seodangdogbe.news.Repository.NewsRepository;
import com.ssafy.seodangdogbe.news.domain.MetaNews;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.dto.NewsDetailsResponseDto;
import com.ssafy.seodangdogbe.news.dto.NewsResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class NewsService {

    public final NewsRepository newsRepository;
    public final NewsDetailsRepository newsDetailsRepository;
    
    // newsSeq(Long)로 mysql의 news 테이블조회
    public NewsResponseDto getNewsPreview(Long newsSeq){
        System.out.println("NewsService - getNewsPreview");

        Optional<News> findNews = newsRepository.findByNewsSeq(newsSeq);
        if (findNews.isPresent()){
            System.out.println(findNews);
            return new NewsResponseDto(findNews.get());
        }
        System.out.println("Optional is empty");
        return null;
    }

    // newsAccessId(String)로 mongodb의 news collection에서 뉴스 조회
    public NewsDetailsResponseDto getNewsDetails(String id){
        System.out.println("NewsService - getNewsDetails");

        Optional<MetaNews> findMetaNews = newsDetailsRepository.findById(id);
        System.out.println("NewsService - findMetaNews : "+findMetaNews.toString());

        if (findMetaNews.isPresent()){
            NewsDetailsResponseDto dto = new NewsDetailsResponseDto(findMetaNews.get());
            return dto;
        }
        System.out.println("findMetaNews is Empty");
        return null;
    }



}
