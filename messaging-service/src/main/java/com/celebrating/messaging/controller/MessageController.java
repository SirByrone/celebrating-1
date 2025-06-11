package com.celebrating.messaging.controller;

import com.celebrating.messaging.model.Message;
import com.celebrating.messaging.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public Mono<Message> sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/conversation")
    public Flux<Message> getConversation(
            @RequestParam Long userId1,
            @RequestParam Long userId2) {
        return messageService.getConversation(userId1, userId2);
    }

    @GetMapping("/unread/{receiverId}")
    public Flux<Message> getUnreadMessages(@PathVariable Long receiverId) {
        return messageService.getUnreadMessages(receiverId);
    }

    @PostMapping("/read")
    public Mono<Void> markConversationAsRead(
            @RequestParam Long receiverId,
            @RequestParam Long senderId) {
        return messageService.markConversationAsRead(receiverId, senderId);
    }
}