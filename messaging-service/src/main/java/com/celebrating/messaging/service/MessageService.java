package com.celebrating.messaging.service;

import com.celebrating.messaging.model.Message;
import com.celebrating.messaging.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Mono<Message> sendMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        message.setRead(false);
        return messageRepository.save(message);
    }

    public Flux<Message> getConversation(Long userId1, Long userId2) {
        return messageRepository.findConversation(userId1, userId2);
    }

    public Flux<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiverIdAndIsReadFalse(receiverId);
    }

    public Mono<Void> markConversationAsRead(Long receiverId, Long senderId) {
        return messageRepository.markConversationAsRead(receiverId, senderId);
    }

    public Mono<Message> markMessageAsRead(Long messageId) {
        return messageRepository.findById(messageId)
                .flatMap(message -> {
                    message.setRead(true);
                    message.setUpdatedAt(LocalDateTime.now());
                    return messageRepository.save(message);
                });
    }
}