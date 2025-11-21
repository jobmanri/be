package com.example.bak.privatemessage.presentation;

public record MessageRequest(
        Long senderId,
        Long receiverId,
        String content
) {

}