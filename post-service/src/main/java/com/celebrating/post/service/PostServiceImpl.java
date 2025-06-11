package com.celebrating.post.service;

import com.celebrating.post.model.Post;
import com.celebrating.post.model.PostStatus;
import com.celebrating.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    
    private final PostRepository postRepository;
    
    @Override
    public Mono<Post> createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setStatus(PostStatus.ACTIVE);
        post.setLikesCount(0);
        post.setCommentsCount(0);
        return postRepository.save(post);
    }
    
    @Override
    public Mono<Post> updatePost(Long id, Post post) {
        return postRepository.findById(id)
            .flatMap(existingPost -> {
                existingPost.setTitle(post.getTitle());
                existingPost.setContent(post.getContent());
                existingPost.setCelebrationType(post.getCelebrationType());
                existingPost.setMediaUrls(post.getMediaUrls());
                existingPost.setUpdatedAt(LocalDateTime.now());
                return postRepository.save(existingPost);
            });
    }
    
    @Override
    public Mono<Post> getPost(Long id) {
        return postRepository.findById(id);
    }
    
    @Override
    public Mono<Void> deletePost(Long id) {
        return postRepository.findById(id)
            .flatMap(post -> {
                post.setStatus(PostStatus.DELETED);
                return postRepository.save(post);
            })
            .then();
    }
    
    @Override
    public Flux<Post> getUserPosts(Long userId) {
        return postRepository.findUserPosts(userId);
    }
    
    @Override
    public Flux<Post> getRecentPosts(int page, int size) {
        return postRepository.findRecentPosts(size, page * size);
    }
    
    @Override
    public Flux<Post> getPostsByCelebrationType(String celebrationType) {
        return postRepository.findByCelebrationType(celebrationType);
    }
} 