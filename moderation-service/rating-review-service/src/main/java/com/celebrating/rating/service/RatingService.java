package com.celebrating.rating.service;

import com.celebrating.rating.dto.RatingRequest;
import com.celebrating.rating.model.Rating;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingService {
    Mono<Rating> ratePost(Long userId, RatingRequest request);
    Mono<Rating> updateRating(Long userId, Long ratingId, RatingRequest request);
    Mono<Void> deleteRating(Long userId, Long ratingId);
    Mono<Rating> getUserPostRating(Long userId, Long postId);
    Mono<Double> getAverageRating(Long postId);
    Mono<Long> getRatingCount(Long postId);
    Flux<Rating> getPostRatings(Long postId);
    Flux<Rating> getUserRatings(Long userId);
} 