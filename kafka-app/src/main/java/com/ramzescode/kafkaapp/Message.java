package com.ramzescode.kafkaapp;

import java.time.LocalDateTime;

public class Message {

    private String message;
    private LocalDateTime createdAt;

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Message(String message, LocalDateTime createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
