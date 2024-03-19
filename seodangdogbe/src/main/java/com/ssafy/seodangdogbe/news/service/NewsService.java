package com.ssafy.seodangdogbe.news.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seodangdogbe.common.MsgResponseDto;
import com.ssafy.seodangdogbe.news.repository.NewsDetailsRepository;
import com.ssafy.seodangdogbe.news.repository.NewsRepository;
import com.ssafy.seodangdogbe.news.repository.UserNewsRepository;
import com.ssafy.seodangdogbe.news.domain.MetaNews;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.user.domain.User;
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
    public MsgResponseDto setUserNewsRead(int userSeq, UserNewsReadRequestDto dto){
        // 뉴스 시퀀스, 형광펜 리스트, 단어 리스트
        Long newsSeq = dto.getNewsSeq();    // 뉴스시퀀스 + 사용자시퀀스(jwt에서 가져오기)
        UserNews findUserNews = jpaQueryFactory
                .selectFrom(userNews)
                .where(userNews.user.userSeq.eq(userSeq)
                        .and(userNews.news.newsSeq.eq(newsSeq)))
                .fetchOne();

        // 찾은 user news의 읽기 내역 업데이트
        findUserNews.setHighlightList(dto.getHighlightList());
        findUserNews.setWordList(dto.getWordList());

        return new MsgResponseDto("사용자 뉴스읽기 저장 성공");
    }

    public MsgResponseDto setUserNewsSolve(int userSeq, UserNewsSolveRequestDto dto){
        // 뉴스 시퀀스, 정답 리스트, 요약(요약 키워드)
        Long newsSeq = dto.getNewsSeq();    // 뉴스시퀀스 + 사용자시퀀스(jwt에서 가져오기)
        UserNews findUserNews = jpaQueryFactory
                .selectFrom(userNews)
                .where(userNews.user.userSeq.eq(userSeq)
                        .and(userNews.news.newsSeq.eq(newsSeq)))
                .fetchOne();

        // 찾은 user news의 풀이 내역 업데이트 & 풀이여부 True 변경
        findUserNews.setSolved(true);
        findUserNews.setUserAnswerList(dto.getUserAnswerList());
        findUserNews.setUserSummary(dto.getUserSummary());

        return new MsgResponseDto("사용자 뉴스풀이 저장 성공");
    }

    public boolean getUserNews(int userSeq, Long newsSeq) {
        UserNews findUserNews = userNewsRepository.findByUserUserSeqAndNewsNewsSeq(userSeq, newsSeq);
        if (findUserNews != null){
            return true;
        } else {
            System.out.println("사용자-뉴스 접근기록이 없습니다.");
            return false;
        }
    }

    public void setUserNewsInit(int userSeq, Long newsSeq) {
        UserNews initUserNews = new UserNews(userSeq, newsSeq);
        userNewsRepository.save(initUserNews);
    }
}
