package com.ssafy.seodangdogbe.word.service;

import com.ssafy.seodangdogbe.common.JsonConverter;
import com.ssafy.seodangdogbe.word.domain.MetaWord;
import com.ssafy.seodangdogbe.word.domain.UserWord;
import com.ssafy.seodangdogbe.word.repository.MetaWordRepository;
import com.ssafy.seodangdogbe.word.repository.UserWordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.ssafy.seodangdogbe.word.dto.KorApiDto.*;
import static com.ssafy.seodangdogbe.word.dto.WordDto.*;


@Service
@Transactional
@RequiredArgsConstructor
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

    public boolean isExistWord(String word){
        return metaWordRepository.existsByWord(word);
    }

    public MetaWordDto findMetaWord(String word){
        return new MetaWordDto(metaWordRepository.findByWord(word).get());
    }

    // 뉴스 상세보기에서 영어 단어 조회
    public void getEngWord(String word) {
    }

    // 표준국어대사전 검색 OpenAPI
    public KorApiSearchDto callStDictSearchApi(String word) throws Exception {
        String key = "EFAB63B197416ACEB79CFBC56E615EE2";

        StringBuffer result = new StringBuffer();
        String strResult = "";
        try {
            StringBuilder urlBuilder = new StringBuilder("https://stdict.korean.go.kr/api/search.do");
            urlBuilder.append("?key="+key);
            urlBuilder.append("&q="+ URLEncoder.encode(word, "UTF-8"));
            urlBuilder.append("&req_type=json");

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Request 형식 설정
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            // 응답 데이터 받아오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            while ((line = br.readLine()) != null){
                result.append(line);
            }
            br.close();
            conn.disconnect();
            strResult = result.toString();

            KorApiSearchDto korApiSearchDto = JsonConverter.apiJsonToKorApiSearchDto(strResult);

            return korApiSearchDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public MetaWord saveMetaWordToMongodb(MetaWordDto metaWordDto){
        MetaWord metaWord = new MetaWord(metaWordDto);
        return metaWordRepository.save(metaWord);
    }


    public boolean setDelete(int userSeq, String word) {
        Optional<UserWord> findUserWord = userWordRepository.findByUserUserSeqAndWord(userSeq, word);
        findUserWord.ifPresent(userWord -> userWord.setDelete(true));
        return true;
    }
}
