package com.celebrating.rating.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("reviews")
public class Review {
    @Id
    private Long id;
    
    @NotNull(message = "User ID is required")
    @Column("user_id")
    private Long userId;
    
    @NotNull(message = "Post ID is required")
    @Column("post_id")
    private Long postId;
    
    @NotBlank(message = "Content is required")
    @Size(min = 10, message = "Review content must be at least 10 characters")
    private String content;
    
    private ReviewStatus status;
    
    @Column("likes_count")
    private int likesCount;
    
    @Column("created_at")
    private LocalDateTime createdAt;
    
    @Column("updated_at")
    private LocalDateTime updatedAt;
} 