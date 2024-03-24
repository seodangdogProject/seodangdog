package com.ssafy.seodangdogbe.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.List;

import static com.ssafy.seodangdogbe.word.dto.KorApiDto.*;

public class JsonConverter implements AttributeConverter<Object, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            // 처리할 예외 처리 로직 추가
            throw new RuntimeException("Error converting Dto to Json", e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, List.class);
        } catch (IOException e) {
            // 처리할 예외 처리 로직 추가
            throw new RuntimeException("Error converting Json to Dto", e);
        }
    }

    public static KorApiSearchDto apiJsonToKorApiSearchDto(String json){
        try {
            return objectMapper.readValue(json, KorApiSearchDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
