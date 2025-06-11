package com.celebrating.newsfeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news-feed")
public class NewsFeedController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/posts")
    public List<Map<String, Object>> getFeed(@RequestHeader("Authorization") String token) {
        // Fetch posts from Post Service
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/post/all", List.class);
        return response.getBody();
    }
}