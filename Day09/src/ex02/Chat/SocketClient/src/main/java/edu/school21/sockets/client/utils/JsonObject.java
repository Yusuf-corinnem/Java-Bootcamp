package edu.school21.sockets.client.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonObject {
    @JsonProperty("message")
    private String message;

    @JsonProperty("senderId")
    private Long senderId;

    @JsonProperty("roomId")
    private Long roomId;

    public JsonObject() {
    }

    public JsonObject(String message, Long senderId, Long roomId) {
        this.message = message;
        this.senderId = senderId;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"message\": \"" + message + "\"" +
                ", \"senderId\": " + senderId +
                ", \"roomId\": " + roomId +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}