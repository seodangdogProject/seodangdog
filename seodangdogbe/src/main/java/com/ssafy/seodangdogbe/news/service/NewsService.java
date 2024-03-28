package com.ssafy.seodangdogbe.news.service;

import com.ssafy.seodangdogbe.auth.repository.UserRepository;
import com.ssafy.seodangdogbe.keyword.dto.NewsRefreshRequestDto;
import com.ssafy.seodangdogbe.keyword.repository.UserKeywordRepository;
import com.ssafy.seodangdogbe.news.domain.KeywordNews;
import com.ssafy.seodangdogbe.news.repository.MetaNewsRepository;
import com.ssafy.seodangdogbe.news.repository.NewsRepository;
import com.ssafy.seodangdogbe.news.repository.UserNewsRepository;
import com.ssafy.seodangdogbe.news.domain.MetaNews;
import com.ssafy.seodangdogbe.news.domain.News;
import com.ssafy.seodangdogbe.news.domain.UserNews;
import com.ssafy.seodangdogbe.user.domain.User;
import com.ssafy.seodangdogbe.user.domain.UserExp;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssafy.seodangdogbe.news.dto.MetaNewsDto.*;
import static com.ssafy.seodangdogbe.news.dto.NewsDto.*;
import static com.ssafy.seodangdogbe.news.dto.UserNewsDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {
    public final UserRepository userRepository;
    public final NewsRepository newsRepository;
    public final MetaNewsRepository metaNewsRepository;
    public final UserNewsRepository userNewsRepository;
    public final UserKeywordRepository userKeywordRepository;

    // mysql뉴스 조회
    public NewsResponseDto getNewsPreview(Long newsSeq){
        Optional<News> findNews = newsRepository.findByNewsSeq(newsSeq);
        if (findNews.isPresent()){
            System.out.println(findNews);
            return new NewsResponseDto(findNews.get());
        }
        System.out.println("findNews(mysql) is empty");
        return null;
    }

    // meta뉴스 조회
    public MetaNewsResponseDto getNewsDetails(String id){
        Optional<MetaNews> findMetaNews = metaNewsRepository.findById(id);

        if (findMetaNews.isPresent()){
            return new MetaNewsResponseDto(findMetaNews.get());
        }
        System.out.println("findMetaNews(mongodb) is Empty");
        return null;
    }

    // newsSeq로 -> meta뉴스까지 조회
    public MetaNewsResponseDto getNewsDetailsByNewsSeq(Long newsSeq){
        Optional<News> findNews = newsRepository.findByNewsSeq(newsSeq);
        if (findNews.isPresent()) {
            Optional<MetaNews> findMetaNews = metaNewsRepository.findById(findNews.get().getNewsAccessId());
            if (findMetaNews.isPresent()) {
                System.out.println(findMetaNews.get().getId());
                return new MetaNewsResponseDto(findMetaNews.get());
            }
            System.out.println("뉴스 원본 데이터 없음.");
            return null;
        }
        System.out.println("뉴스 미리보기 데이터 없음");
        return null;
    }



    /* -------------------------- UserExp Table -------------------------- */
    public void plusUserExp(int userSeq){

        Optional<User> user = userRepository.findById(userSeq);
        if (user.isPresent()){
            UserExp userExp = user.get().getUserExp();
        }
    }


    /* -------------------------- UserNews Table -------------------------- */
    // 뉴스 읽기 저장(읽기만 하고 나갈때, 문제 풀기 시작했을 때)
    public boolean setUserNewsRead(int userSeq, UserNewsReadRequestDto dto){
        // 뉴스 시퀀스, 형광펜 리스트, 단어 리스트
        Long newsSeq = dto.getNewsSeq();
        UserNews findUserNews = userNewsRepository.findByUserUserSeqAndNewsNewsSeq(userSeq, newsSeq)
                .orElseThrow(() -> new NullPointerException("사용자뉴스가 존재하지 않습니다."));

        // 출력
        System.out.println(findUserNews.getUser().getUserId() + " & " + findUserNews.getNews().getNewsSeq());
        System.out.println(dto.getHighlightList().toString());
        System.out.println(dto.getWordList().toString());

        // 찾은 user news의 읽기 내역 업데이트
        findUserNews.setHighlightList(dto.getHighlightList());
        findUserNews.setWordList(dto.getWordList());

        System.out.println("사용자 뉴스읽기 저장 성공");
        return true;
    }

    // 뉴스 풀이 저장(요약까지 제출했을 때)
    public boolean setUserNewsSolve(int userSeq, UserNewsSolveRequestDto dto){
        // 뉴스 시퀀스, 정답 리스트, 요약(요약 키워드)
        Long newsSeq = dto.getNewsSeq();

        News news = newsRepository.findById(newsSeq).orElseThrow(() -> new NullPointerException("뉴스시퀀스가 존재하지 않습니다."));
        // 뉴스 자체에 풀이 횟수 증가
        int count = news.getCountSolve();
        news.setCountSolve(count + 1);

        UserNews findUserNews = userNewsRepository.findByUserUserSeqAndNewsNewsSeq(userSeq, newsSeq)
                .orElseThrow(() -> new NullPointerException("사용자뉴스가 존재하지 않습니다."));

        // 찾은 user news의 풀이 내역 업데이트 & 풀이여부 True 변경
        findUserNews.setSolved(true);
        findUserNews.setUserAnswerList(dto.getUserAnswerList());
        findUserNews.setUserSummary(dto.getUserSummary());

        // ** 채점 -> 더 효율적인 방법으로 바꾸기
        User user = userRepository.findById(userSeq).orElseThrow(NullPointerException::new);
        UserExp userExp = user.getUserExp();
        List<Boolean> correctList = dto.getCorrectList();
        
        if (correctList.get(0)){    // 어휘
            int exp = userExp.getWordExp();
            userExp.setWordExp(exp + 1);
        }
        if (correctList.get(1)){    // 추론
            int exp = userExp.getInferenceExp();
            userExp.setInferenceExp(exp + 1);
        }
        if (correctList.get(2)){    // 판단
            int exp = userExp.getJudgementExp();
            userExp.setJudgementExp(exp + 1);
        }

        // 요약 경험치 증가
        int exp = userExp.getSummaryExp();
        userExp.setSummaryExp(exp + 1);

        List<String> keywordList = new ArrayList<>();
        for (KeywordNews newsKeyword : news.getKeywordNewsList()){
            keywordList.add(newsKeyword.getKeyword().getKeyword());
        }

        // 뉴스에 포함된 키워드 가중치 증가
        userKeywordRepository.incrementKeywordWeight(user, keywordList, 0.99);

        System.out.println("사용자 뉴스풀이 저장 성공");
        return true;
    }

    // 사용자-뉴스 테이블 존재 여부 판단(최초접근 여부 판단)
    public boolean getUserNewsExist(int userSeq, Long newsSeq) {
        UserNews findUserNews = userNewsRepository.findByUserUserSeqAndNewsNewsSeq(userSeq, newsSeq)
                .orElse(null);
        if (findUserNews != null){
            return true;
        } else {
            System.out.println("사용자-뉴스 접근기록이 없습니다.");
            return false;
        }
    }

    // 사용자-뉴스 테이블 저장(최초조회)
    public void setUserNewsInit(int userSeq, Long newsSeq) {
        // 유저나 뉴스가 없는 경우에 대해 예외처리 => .orElseThrow() 사용
        User user = userRepository.findById(userSeq).orElseThrow(NullPointerException::new);
        News news = newsRepository.findByNewsSeq(newsSeq).orElseThrow(NullPointerException::new);

        UserNews initUserNews = new UserNews(user, news);
        userNewsRepository.save(initUserNews);

        // 뉴스 자체에 조회수 추가
        int count = news.getCountView();
        news.setCountView(count + 1);

        // 뉴스읽기 경험치 증가
        UserExp userExp = user.getUserExp();
        int exp = userExp.getNewsExp();
        userExp.setNewsExp(exp + 1);
    }

    // 사용자-뉴스 기록(읽기/읽기+풀이) 조회
    public UserNewsResponseDto getReadOrSolveRecord(int userSeq, Long newsSeq) {
        UserNews findUserNews = userNewsRepository.findByUserUserSeqAndNewsNewsSeq(userSeq, newsSeq)
                .orElseThrow(() -> new NullPointerException("사용자뉴스가 존재하지 않습니다."));
        UserNewsResponseDto dto = new UserNewsResponseDto(findUserNews.getHighlightList(), findUserNews.getWordList());
        if (findUserNews.isSolved()) {
            dto.setUserAnswers(findUserNews.getUserAnswerList());
            dto.setUserSummary(findUserNews.getUserSummary());
        }
        return dto;
    }
}
