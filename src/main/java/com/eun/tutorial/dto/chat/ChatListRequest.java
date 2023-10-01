package com.eun.tutorial.dto.chat;

import lombok.Data;

@Data
public class ChatListRequest {
	private String roomId;
    private int page;
    private int size;
}

