package com.celebrating.messaging.websocket;

import lombok.Data;

@Data
public class WebSocketMessage {
    private MessageType type;
    private Object payload;
    private Long senderId;
    private Long receiverId;

    public enum MessageType {
        CHAT,
        TYPING,
        READ_RECEIPT,
        USER_STATUS
    }
}