package com.example.bak.privatemessage.domain;

import com.example.bak.global.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "private_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MessageParticipants messageParticipants;

    @Embedded
    private MessageState messageState;

    @Column(nullable = false)
    private String content;

    private Message(
            MessageParticipants messageParticipants,
            String content
    ) {
        this.messageParticipants = messageParticipants;
        this.content = content;
    }

    public static Message create(
            Long senderId,
            Long receiverId,
            String content
    ) {
        MessageParticipants participants = MessageParticipants.of(senderId, receiverId);
        Message message = new Message(participants, content);
        message.messageState = MessageState.init();
        return message;
    }

    @PostLoad
    private void postLoad() {
        if (this.messageState == null) {
            this.messageState = MessageState.init();
        }
    }
}
