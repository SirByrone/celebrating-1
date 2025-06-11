package com.celebrating.messaging.websocket;

import com.celebrating.messaging.model.Message;
import com.celebrating.messaging.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final MessageService messageService;
    private final ObjectMapper objectMapper;
    private final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = extractUserId(session);
        if (userId != null) {
            userSessions.put(userId, session);
            log.info("User {} connected", userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Message message = objectMapper.readValue(textMessage.getPayload(), Message.class);
        
        // Save message to database
        messageService.sendMessage(message)
            .subscribe(savedMessage -> {
                try {
                    // Send to receiver if online
                    WebSocketSession receiverSession = userSessions.get(message.getReceiverId());
                    if (receiverSession != null && receiverSession.isOpen()) {
                        String messageJson = objectMapper.writeValueAsString(savedMessage);
                        receiverSession.sendMessage(new TextMessage(messageJson));
                    }
                } catch (Exception e) {
                    log.error("Error sending message to receiver", e);
                }
            });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = extractUserId(session);
        if (userId != null) {
            userSessions.remove(userId);
            log.info("User {} disconnected", userId);
        }
    }

    private Long extractUserId(WebSocketSession session) {
        try {
            String userId = session.getUri().getQuery().split("userId=")[1];
            return Long.parseLong(userId);
        } catch (Exception e) {
            log.error("Error extracting user ID from session", e);
            return null;
        }
    }
}