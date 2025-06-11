package com.celebrating.rating.controller;

import com.celebrating.rating.dto.ReviewRequest;
import com.celebrating.rating.model.Review;
import com.celebrating.rating.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Review> createReview(
            @RequestHeader("X-User-ID") Long userId,
            @Valid @RequestBody ReviewRequest request) {
        return reviewService.createReview(userId, request);
    }

    @PutMapping("/{reviewId}")
    public Mono<Review> updateReview(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request) {
        return reviewService.updateReview(userId, reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteReview(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long reviewId) {
        return reviewService.deleteReview(userId, reviewId);
    }

    @GetMapping("/posts/{postId}/user")
    public Mono<Review> getUserPostReview(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long postId) {
        return reviewService.getUserPostReview(userId, postId);
    }

    @GetMapping("/posts/{postId}")
    public Flux<Review> getPostReviews(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return reviewService.getPostReviews(postId, page, size);
    }

    @GetMapping("/users/{userId}")
    public Flux<Review> getUserReviews(@PathVariable Long userId) {
        return reviewService.getUserReviews(userId);
    }

    @GetMapping("/posts/{postId}/count")
    public Mono<Long> getReviewCount(@PathVariable Long postId) {
        return reviewService.getReviewCount(postId);
    }

    @PostMapping("/{reviewId}/like")
    public Mono<Review> likeReview(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long reviewId) {
        return reviewService.likeReview(userId, reviewId);
    }

    @DeleteMapping("/{reviewId}/like")
    public Mono<Review> unlikeReview(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long reviewId) {
        return reviewService.unlikeReview(userId, reviewId);
    }
} 