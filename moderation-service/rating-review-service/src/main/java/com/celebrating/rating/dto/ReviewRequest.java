package com.celebrating.rating.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull(message = "Post ID is required")
    private Long postId;
    
    @NotBlank(message = "Content is required")
    @Size(min = 10, message = "Review content must be at least 10 characters")
    private String content;
} 