package com.eun.tutorial.dto.main;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String postId;
    private String content;
    private String createId;
    private LocalDateTime createdAt;
}
