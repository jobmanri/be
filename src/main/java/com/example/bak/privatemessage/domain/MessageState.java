package com.example.bak.privatemessage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class MessageState {

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "deleted_at_by_sender")
    private LocalDateTime deletedAtBySender;

    @Column(name = "deleted_at_by_receiver")
    private LocalDateTime deletedAtByReceiver;

    public static MessageState init() {
        return new MessageState();
    }

    public void markAsRead() {
        if (this.readAt == null) {
            this.readAt = LocalDateTime.now();
        }
    }

    public void deleteBySender() {
        this.deletedAtBySender = LocalDateTime.now();
    }

    public void deleteByReceiver() {
        this.deletedAtByReceiver = LocalDateTime.now();
    }
}