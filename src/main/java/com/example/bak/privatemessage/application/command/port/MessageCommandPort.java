package com.example.bak.privatemessage.application.command.port;

import com.example.bak.privatemessage.domain.Message;
import com.example.bak.privatemessage.domain.MessageParticipants;
import java.util.Optional;

public interface MessageCommandPort {

    Message save(Message message);

    void markAsRead(MessageParticipants participants);

    Optional<Message> findById(Long messageId);
}
