package com.celebrating.post.controller;

import com.celebrating.post.model.Post;
import com.celebrating.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/create")
    public Post createPost(@RequestBody Map<String, String> payload) {
        Post post = new Post();
        post.setUserId(payload.get("userId"));
        post.setContent(payload.get("content"));
        post.setImageBase64(payload.get("imageBase64"));
        post.setLikes(new ArrayList<>());
        post.setComments(new ArrayList<>());
        post.setEvents(new ArrayList<>());
        return postRepository.save(post);
    }

    @PostMapping("/like/{postId}")
    public Map<String, Object> likePost(@PathVariable String postId, @RequestHeader("Authorization") String token) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        String userId = extractUserIdFromToken(token); // Implement token parsing
        List<String> likes = post.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
        } else {
            likes.add(userId);
        }
        post.setLikes(likes);
        postRepository.save(post);
        return Map.of("success", true);
    }

    @PostMapping("/comment/{postId}")
    public Map<String, Object> addComment(@PathVariable String postId, @RequestBody Map<String, String> payload, @RequestHeader("Authorization") String token) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        String userId = extractUserIdFromToken(token);
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setContent(payload.get("content"));
        List<Comment> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);
        postRepository.save(post);
        return Map.of("success", true);
    }
}