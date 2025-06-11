package com.celebrating.post.dto;

import com.celebrating.post.model.CelebrationType;
import com.celebrating.post.model.PostStatus;
import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostResponse {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private CelebrationType celebrationType;
    private List<String> mediaUrls;
    private int likesCount;
    private int commentsCount;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional fields for Flutter UI
    private String userDisplayName; // Will be populated from user-service
    private String userAvatarUrl;  // Will be populated from user-service
    private boolean isLikedByCurrentUser; // Will be populated based on user context
} 