package com.celebrating.rating.repository;

import com.celebrating.rating.model.Rating;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingRepository extends ReactiveCrudRepository<Rating, Long> {
    
    @Query("SELECT * FROM ratings WHERE user_id = :userId AND post_id = :postId")
    Mono<Rating> findByUserIdAndPostId(Long userId, Long postId);
    
    @Query("SELECT AVG(rating_value) FROM ratings WHERE post_id = :postId")
    Mono<Double> getAverageRatingForPost(Long postId);
    
    @Query("SELECT COUNT(*) FROM ratings WHERE post_id = :postId")
    Mono<Long> getRatingCountForPost(Long postId);
    
    Flux<Rating> findByPostId(Long postId);
    
    Flux<Rating> findByUserId(Long userId);
} 