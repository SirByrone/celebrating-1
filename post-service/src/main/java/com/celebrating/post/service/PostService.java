package com.celebrating.post.service;

import com.celebrating.post.model.Post;
import com.celebrating.post.model.Comment;
import com.celebrating.post.model.Like;
import com.celebrating.post.repository.PostRepository;
import com.celebrating.post.repository.CommentRepository;
import com.celebrating.post.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public Mono<Post> createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setLikesCount(0);
        post.setCommentsCount(0);
        return postRepository.save(post);
    }

    public Mono<Post> getPost(Long id) {
        return postRepository.findById(id);
    }

    public Flux<Post> getUserPosts(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public Flux<Post> getRecentPosts(int page, int size) {
        return postRepository.findRecentPosts(size, page * size);
    }

    public Mono<Comment> addComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment)
                .flatMap(savedComment -> 
                    postRepository.updateCommentsCount(comment.getPostId(), 1)
                        .thenReturn(savedComment)
                );
    }

    public Flux<Comment> getPostComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Mono<Like> likePost(Like like) {
        like.setCreatedAt(LocalDateTime.now());
        return likeRepository.findByPostIdAndUserId(like.getPostId(), like.getUserId())
                .flatMap(existingLike -> Mono.<Like>error(new RuntimeException("Already liked")))
                .switchIfEmpty(
                    likeRepository.save(like)
                        .flatMap(savedLike -> 
                            postRepository.updateLikesCount(like.getPostId(), 1)
                                .thenReturn(savedLike)
                        )
                );
    }

    public Mono<Void> unlikePost(Long postId, Long userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId)
                .flatMap(like -> 
                    likeRepository.delete(like)
                        .then(postRepository.updateLikesCount(postId, -1))
                );
    }

    public Flux<Like> getPostLikes(Long postId) {
        return likeRepository.findByPostId(postId);
    }
} 