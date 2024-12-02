package edu.school21.sockets.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonFormatConverter {
    private ObjectMapper objectMapper;

    public JsonFormatConverter() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public String convertToJson(String message, Long senderId, Long roomId) {
        JsonObject jsonObject = new JsonObject(message, senderId, roomId);
        try {
            return objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonObject convertJsonToObject(String json) {
        try {
            return objectMapper.readValue(json, JsonObject.class);
        } catch (JsonProcessingException ex) {
            System.out.println("Ошибка при парсинге JSON: " + ex.getMessage());
            return null;
        }
    }
}
