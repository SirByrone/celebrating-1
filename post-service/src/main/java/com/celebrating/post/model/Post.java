package com.celebrating.post.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String userId;
    private String content;
    private String imageBase64;
    private List<String> likes;
    private List<Comment> comments;
    private List<Event> events;

    // Getters and Setters
}

class Comment {
    private String id;
    private String userId;
    private String content;
    // Getters and Setters
}

class Event {
    private String title;
    private String date;
    private String location;
    private String description;
    // Getters and Setters
}