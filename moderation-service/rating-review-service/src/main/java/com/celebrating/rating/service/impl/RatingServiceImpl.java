package com.celebrating.rating.service.impl;

import com.celebrating.rating.dto.RatingRequest;
import com.celebrating.rating.model.Rating;
import com.celebrating.rating.repository.RatingRepository;
import com.celebrating.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public Mono<Rating> ratePost(Long userId, RatingRequest request) {
        return ratingRepository.findByUserIdAndPostId(userId, request.getPostId())
            .flatMap(existingRating -> {
                existingRating.setRatingValue(request.getRatingValue());
                existingRating.setUpdatedAt(LocalDateTime.now());
                return ratingRepository.save(existingRating);
            })
            .switchIfEmpty(
                ratingRepository.save(Rating.builder()
                    .userId(userId)
                    .postId(request.getPostId())
                    .ratingValue(request.getRatingValue())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build())
            );
    }

    @Override
    public Mono<Rating> updateRating(Long userId, Long ratingId, RatingRequest request) {
        return ratingRepository.findById(ratingId)
            .filter(rating -> rating.getUserId().equals(userId))
            .flatMap(rating -> {
                rating.setRatingValue(request.getRatingValue());
                rating.setUpdatedAt(LocalDateTime.now());
                return ratingRepository.save(rating);
            });
    }

    @Override
    public Mono<Void> deleteRating(Long userId, Long ratingId) {
        return ratingRepository.findById(ratingId)
            .filter(rating -> rating.getUserId().equals(userId))
            .flatMap(ratingRepository::delete);
    }

    @Override
    public Mono<Rating> getUserPostRating(Long userId, Long postId) {
        return ratingRepository.findByUserIdAndPostId(userId, postId);
    }

    @Override
    public Mono<Double> getAverageRating(Long postId) {
        return ratingRepository.getAverageRatingForPost(postId);
    }

    @Override
    public Mono<Long> getRatingCount(Long postId) {
        return ratingRepository.getRatingCountForPost(postId);
    }

    @Override
    public Flux<Rating> getPostRatings(Long postId) {
        return ratingRepository.findByPostId(postId);
    }

    @Override
    public Flux<Rating> getUserRatings(Long userId) {
        return ratingRepository.findByUserId(userId);
    }
} 