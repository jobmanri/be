package com.example.bak.privatemessage.infra.command;

import com.example.bak.privatemessage.application.command.port.MessageCommandPort;
import com.example.bak.privatemessage.domain.Message;
import com.example.bak.privatemessage.domain.MessageParticipants;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageCommandRepositoryAdapter implements MessageCommandPort {

    private final MessageJpaRepository messageJpaRepository;

    @Override
    public Message save(Message message) {
        return messageJpaRepository.save(message);
    }

    @Override
    public void markAsRead(MessageParticipants participants) {
        messageJpaRepository.markAsReadByParticipants(
                participants.getSenderId(),
                participants.getReceiverId()
        );
    }

    @Override
    public Optional<Message> findById(Long messageId) {
        return messageJpaRepository.findById(messageId);
    }
}
