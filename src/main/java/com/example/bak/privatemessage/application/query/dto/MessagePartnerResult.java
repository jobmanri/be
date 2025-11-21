package com.example.bak.privatemessage.application.query.dto;

public record MessagePartnerResult(
        Long partnerId,
        String nickname,
        Boolean hasUnread
) {

}
