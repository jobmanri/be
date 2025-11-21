package com.example.bak.privatemessage.application.query.dto;

import java.time.LocalDateTime;

public record MessageItemResult(
        Long id,
        Long senderId,
        Long receiverId,
        String content,
        //created_at 추가
        LocalDateTime readAt
) {

}
