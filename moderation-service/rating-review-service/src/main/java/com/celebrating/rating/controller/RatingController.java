package com.celebrating.rating.controller;

import com.celebrating.rating.dto.RatingRequest;
import com.celebrating.rating.model.Rating;
import com.celebrating.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Rating> ratePost(
            @RequestHeader("X-User-ID") Long userId,
            @Valid @RequestBody RatingRequest request) {
        return ratingService.ratePost(userId, request);
    }

    @PutMapping("/{ratingId}")
    public Mono<Rating> updateRating(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long ratingId,
            @Valid @RequestBody RatingRequest request) {
        return ratingService.updateRating(userId, ratingId, request);
    }

    @DeleteMapping("/{ratingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteRating(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long ratingId) {
        return ratingService.deleteRating(userId, ratingId);
    }

    @GetMapping("/posts/{postId}/user")
    public Mono<Rating> getUserPostRating(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long postId) {
        return ratingService.getUserPostRating(userId, postId);
    }

    @GetMapping("/posts/{postId}/average")
    public Mono<Double> getAverageRating(@PathVariable Long postId) {
        return ratingService.getAverageRating(postId);
    }

    @GetMapping("/posts/{postId}/count")
    public Mono<Long> getRatingCount(@PathVariable Long postId) {
        return ratingService.getRatingCount(postId);
    }

    @GetMapping("/posts/{postId}")
    public Flux<Rating> getPostRatings(@PathVariable Long postId) {
        return ratingService.getPostRatings(postId);
    }

    @GetMapping("/users/{userId}")
    public Flux<Rating> getUserRatings(@PathVariable Long userId) {
        return ratingService.getUserRatings(userId);
    }
} 