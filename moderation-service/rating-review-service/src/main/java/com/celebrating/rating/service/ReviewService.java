package com.celebrating.rating.service;

import com.celebrating.rating.dto.ReviewRequest;
import com.celebrating.rating.model.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewService {
    Mono<Review> createReview(Long userId, ReviewRequest request);
    Mono<Review> updateReview(Long userId, Long reviewId, ReviewRequest request);
    Mono<Void> deleteReview(Long userId, Long reviewId);
    Mono<Review> getUserPostReview(Long userId, Long postId);
    Flux<Review> getPostReviews(Long postId, int page, int size);
    Flux<Review> getUserReviews(Long userId);
    Mono<Long> getReviewCount(Long postId);
    Mono<Review> likeReview(Long userId, Long reviewId);
    Mono<Review> unlikeReview(Long userId, Long reviewId);
} 