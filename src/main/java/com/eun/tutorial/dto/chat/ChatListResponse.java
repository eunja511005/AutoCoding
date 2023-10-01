package com.eun.tutorial.dto.chat;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatListResponse {
	private boolean success;
    private List<ChatMessage> messages;
    private long totalElements;

    public ChatListResponse(boolean success, List<ChatMessage> projects, long totalElements) {
        this.success = success;
    	this.messages = projects;
        this.totalElements = totalElements;
    }
}
