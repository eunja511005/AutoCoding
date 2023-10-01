package com.eun.tutorial.dto.chat;

import lombok.Data;

@Data
public class ChatMessage {
	private String id;
    private String roomId;
    private String sender;
    private String message;
    private String timestamp;
    private String delYn;
	private String createId;
	private String createDt;
	private String updateId;
	private String updateDt;

}
