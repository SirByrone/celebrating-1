package com.celebrating.messaging.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/websocket")
public class WebSocketController {

    @GetMapping("/info")
    public Mono<WebSocketInfo> getWebSocketInfo() {
        return Mono.just(new WebSocketInfo("/ws/chat"));
    }

    private record WebSocketInfo(String endpoint) {}
}