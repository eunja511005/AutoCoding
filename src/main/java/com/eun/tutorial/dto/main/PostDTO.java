package com.eun.tutorial.dto.main;

import java.time.LocalDate;
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
    private String visibility;
    private String postType;
    private LocalDate openDate;
    private String createId;
    private LocalDateTime created_at;
}
