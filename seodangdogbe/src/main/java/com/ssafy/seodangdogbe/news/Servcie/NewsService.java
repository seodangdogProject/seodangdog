package com.ssafy.seodangdogbe.news.Servcie;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.news.repository.NewsDetailsRepository;
import com.ssafy.seodangdogbe.news.repository.NewsRepository;
import com.ssafy.seodangdogbe.news.repository.UserNewsRepository;
import com.ssafy.seodangdogbe.news.domain.MetaNews;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssafy.seodangdogbe.news.domain.QUserNews.*;
import static com.ssafy.seodangdogbe.news.dto.NewsDetailsDto.*;
import static com.ssafy.seodangdogbe.news.dto.NewsDto.*;
import static com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    public final JPAQueryFactory jpaQueryFactory;

    public final NewsRepository newsRepository;
    public final NewsDetailsRepository newsDetailsRepository;
    public final UserNewsRepository userNewsRepository;
    
    // newsSeq(Long)로 mysql의 news 테이블조회
    public NewsResponseDto getNewsPreview(Long newsSeq){
        Optional<News> findNews = newsRepository.findByNewsSeq(newsSeq);
        if (findNews.isPresent()){
            System.out.println(findNews);
            return new NewsResponseDto(findNews.get());
        }
        System.out.println("findNews(mysql) is empty");
        return null;
    }

    // newsAccessId(String)로 mongodb의 meta_news collection에서 뉴스 조회
    public NewsDetailsResponseDto getNewsDetails(String id){
        Optional<MetaNews> findMetaNews = newsDetailsRepository.findById(id);

        if (findMetaNews.isPresent()){
            NewsDetailsResponseDto dto = new NewsDetailsResponseDto(findMetaNews.get());
            return dto;
        }
        System.out.println("findMetaNews(mongodb) is Empty");
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

    // 뉴스 읽기 내역 저장(읽기만 하고 나갈때, 문제 풀기 시작했을 때)
    public UserNewsResponseDto saveUserNewsRead(int userSeq, UserNewsReadRequestDto dto){
        // 뉴스 읽기 시퀀스, 형광펜 리스트, 단어 리스트
        Long newsSeq = dto.getNewsSeq();
        UserNews findUserNews = jpaQueryFactory
                .selectFrom(userNews)
                .where(userNews.user.userSeq.eq(userSeq)
                        .and(userNews.news.newsSeq.eq(newsSeq)))
                .fetchOne();

        // 찾은 user news의 읽기 내역 업데이트

        return null;
    }

//    public UserNewsResponseDto saveUserNewsSolve(UserNewsSolveRequestDto dto){
//
//    }
}
