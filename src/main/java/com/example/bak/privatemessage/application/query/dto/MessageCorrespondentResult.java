package com.example.bak.privatemessage.application.query.dto;

public record MessageCorrespondentResult(
        Long correspondentId,
        String nickname,
        Boolean hasUnread
) {

}
