package com.ssafy.seodangdogbe.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeWeightReqDto  {

    Long newsSeq;
    List<KeywordInfo> keywordInfoList = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KeywordInfo {
        String keyword;
        double weight;
    }

    static public List<KeywordInfo> extractKeywordInfoList(List<DeWeightReqDto> dtoList) {
        List<KeywordInfo> keywordInfoList = new ArrayList<>();

        for (DeWeightReqDto dto : dtoList) {
            for (KeywordInfo keywordInfo : dto.keywordInfoList) {
                keywordInfoList.add(keywordInfo);
            }
        }

        return keywordInfoList;
    }

    static public List<InfoDto> extractInfoDtoList(List<DeWeightReqDto> dtoList) {
        List<InfoDto> InfoDtoList = new ArrayList<>();

        for (DeWeightReqDto dto : dtoList) {
            InfoDtoList.add(new InfoDto(dto.newsSeq, 0.5));
        }

        return InfoDtoList;
    }

}

