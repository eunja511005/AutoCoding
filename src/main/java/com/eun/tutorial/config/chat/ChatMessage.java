package com.eun.tutorial.config.chat;

import lombok.Data;

@Data
public class ChatMessage {
    private String sender;
    private String message;
    private String timestamp;

}
