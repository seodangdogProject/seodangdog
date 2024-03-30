package com.ssafy.seodangdogbe.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class DeWeightReqDto {

    Long newSeq;
    KeywordInfo[] keywordInfoList;

    @Data
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
            for (KeywordInfo keywordInfo : dto.keywordInfoList) {
                InfoDtoList.add(new InfoDto(dto.newSeq, keywordInfo.weight));
            }
        }

        return InfoDtoList;
    }

}

