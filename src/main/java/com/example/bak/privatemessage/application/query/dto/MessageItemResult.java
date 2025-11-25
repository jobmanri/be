package com.example.bak.privatemessage.application.query.dto;

import java.time.LocalDateTime;

public record MessageItemResult(
        Long id,
        Long senderId,
        Long receiverId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime readAt
) {

}
