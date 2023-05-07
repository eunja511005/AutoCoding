package com.eun.tutorial.dto.main;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String id;
    private String title;
    private String content;
    private boolean isSecret;
    private LocalDateTime created_at;
}
