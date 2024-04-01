package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.common.JsonConverter;
import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import com.ssafy.seodangdogbe.word.repository.MetaWordRepository;
import com.ssafy.seodangdogbe.word.repository.UserWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.ssafy.seodangdogbe.word.dto.WordApiDto.*;
import static com.ssafy.seodangdogbe.word.dto.WordDto.*;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WordService {
    public final UserWordRepository userWordRepository;
    public final MetaWordRepository metaWordRepository;

    public boolean isKor(String word){
        String regex = "^[가-힣]*$";

        boolean isKoreanOnly = Pattern.matches(regex, word);
        if (isKoreanOnly) {
            System.out.println("한글 단어입니다.");
            return true;
        } else {
            System.out.println("한글 단어가 아닙니다.");
            return false;
        }
    }

    public boolean isEng(String word) {
        String regex = "^[a-zA-Z]*$";

        boolean isEngOnly = Pattern.matches(regex, word);
        if (isEngOnly) {
            System.out.println("영어 단어입니다.");
            return true;
        } else {
            System.out.println("영어 단어가 아닙니다.");
            return false;
        }
    }

    public boolean existWord(String word){
        return metaWordRepository.existsByWord(word);
    }

    public MetaWordDto findMetaWord(String word){
        return new MetaWordDto(metaWordRepository.findByWord(word).orElseThrow(NullPointerException::new));
    }

    // 네이버 백과사전 api
    public EncycApiDto callNEncycSearchApi(String word) {
        String clientId = "7VXk_gxmrs8uoc2k12bm";
        String clientSecret = "P3c4RQb6bJ";

        try {   // ** 검색어 인코딩 실패 예외처리
            String urlBuilder = "https://openapi.naver.com/v1/search/encyc.json"
                    + "?query=" + URLEncoder.encode(word, StandardCharsets.UTF_8)
                    + "&display=10&start=1&sort=sim";


            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // ** api 예외처리

            // Request 형식 설정
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Naver-Client-Id", clientId);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            // 응답 데이터 받아오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();

//            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null){
                result.append(line);
//                sb.append(line);
//                sb.append("\n");
            }
            br.close();
            conn.disconnect();

//            System.out.println("=======================결과=======================");
//            System.out.println(sb.toString());

            return JsonConverter.apiJsonToEncycApiDto(result.toString());
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw new RuntimeException(e);
        }

    }

    // 표준국어대사전 검색 OpenAPI
    public KorApiSearchDto callStDictSearchApi(String word) {
        String key = "EFAB63B197416ACEB79CFBC56E615EE2";

        try {
            String urlBuilder = "https://stdict.korean.go.kr/api/search.do" + "?key=" + key +
                    "&q=" + URLEncoder.encode(word, StandardCharsets.UTF_8) +
                    "&req_type=json";

            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // ** api 예외처리

            // Request 형식 설정
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            // 응답 데이터 받아오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null){
                result.append(line);
            }
            br.close();
            conn.disconnect();

            if (result.isEmpty())
                return null;
            else
                return JsonConverter.apiJsonToKorApiSearchDto(result.toString());
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    // mongodb에 MetaWord 저장
    public void saveMetaWordToMongodb(MetaWordDto metaWordDto){
        MetaWord metaWord = new MetaWord(metaWordDto);
        metaWordRepository.save(metaWord);
    }

    // 단어장에서 단어 상태를 삭제로 변경
    public boolean setDelete(int userSeq, String word) {
        Optional<UserWord> findUserWord = userWordRepository.findByUserUserSeqAndWord(userSeq, word);
        findUserWord.ifPresent(userWord -> userWord.setIsDelete(true));
        return true;
    }

}
