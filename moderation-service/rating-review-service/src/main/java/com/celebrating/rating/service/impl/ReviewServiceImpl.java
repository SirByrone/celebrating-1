package com.celebrating.rating.service.impl;

import com.celebrating.rating.dto.ReviewRequest;
import com.celebrating.rating.model.Review;
import com.celebrating.rating.model.ReviewStatus;
import com.celebrating.rating.repository.ReviewRepository;
import com.celebrating.rating.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Mono<Review> createReview(Long userId, ReviewRequest request) {
        return reviewRepository.findByUserIdAndPostId(userId, request.getPostId())
            .flatMap(existingReview -> {
                existingReview.setContent(request.getContent());
                existingReview.setUpdatedAt(LocalDateTime.now());
                return reviewRepository.save(existingReview);
            })
            .switchIfEmpty(
                reviewRepository.save(Review.builder()
                    .userId(userId)
                    .postId(request.getPostId())
                    .content(request.getContent())
                    .status(ReviewStatus.ACTIVE)
                    .likesCount(0)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build())
            );
    }

    @Override
    public Mono<Review> updateReview(Long userId, Long reviewId, ReviewRequest request) {
        return reviewRepository.findById(reviewId)
            .filter(review -> review.getUserId().equals(userId))
            .flatMap(review -> {
                review.setContent(request.getContent());
                review.setUpdatedAt(LocalDateTime.now());
                return reviewRepository.save(review);
            });
    }

    @Override
    public Mono<Void> deleteReview(Long userId, Long reviewId) {
        return reviewRepository.findById(reviewId)
            .filter(review -> review.getUserId().equals(userId))
            .flatMap(review -> {
                review.setStatus(ReviewStatus.DELETED);
                return reviewRepository.save(review);
            })
            .then();
    }

    @Override
    public Mono<Review> getUserPostReview(Long userId, Long postId) {
        return reviewRepository.findByUserIdAndPostId(userId, postId);
    }

    @Override
    public Flux<Review> getPostReviews(Long postId, int page, int size) {
        return reviewRepository.findByPostId(postId, size, page * size);
    }

    @Override
    public Flux<Review> getUserReviews(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Mono<Long> getReviewCount(Long postId) {
        return reviewRepository.getReviewCountForPost(postId);
    }

    @Override
    public Mono<Review> likeReview(Long userId, Long reviewId) {
        return reviewRepository.findById(reviewId)
            .flatMap(review -> {
                review.setLikesCount(review.getLikesCount() + 1);
                return reviewRepository.save(review);
            });
    }

    @Override
    public Mono<Review> unlikeReview(Long userId, Long reviewId) {
        return reviewRepository.findById(reviewId)
            .flatMap(review -> {
                review.setLikesCount(Math.max(0, review.getLikesCount() - 1));
                return reviewRepository.save(review);
            });
    }
} 