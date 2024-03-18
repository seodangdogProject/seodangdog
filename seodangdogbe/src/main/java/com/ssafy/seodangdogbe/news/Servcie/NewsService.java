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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class NewsService {

    public final NewsRepository newsRepository;
    public final NewsDetailsRepository newsDetailsRepository;
    
    // newsSeq(Long)로 mysql의 news 테이블조회
    public NewsResponseDto getNewsPreview(Long newsSeq){
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
        Optional<MetaNews> findMetaNews = newsDetailsRepository.findById(id);

        if (findMetaNews.isPresent()){
            NewsDetailsResponseDto dto = new NewsDetailsResponseDto(findMetaNews.get());
            return dto;
        }
        System.out.println("findMetaNews is Empty");
        return null;
    }

    // 뉴스 메타데이터 전체 목록 가져오기
    public List<NewsDetailsResponseDto> getNewsDetailsList(){
        List<MetaNews> list = newsDetailsRepository.findAll();
        List<NewsDetailsResponseDto> result = new ArrayList<>();
        for (MetaNews mnews : list) {
            result.add(new NewsDetailsResponseDto(mnews));
        }
        return result;
    }

}
