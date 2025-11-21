package com.example.bak.privatemessage.application.command;

import com.example.bak.global.exception.BusinessException;
import com.example.bak.global.exception.ErrorCode;
import com.example.bak.privatemessage.application.command.port.MessageCommandPort;
import com.example.bak.privatemessage.domain.Message;
import com.example.bak.privatemessage.domain.MessageParticipants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MessageCommandService {

    private final MessageCommandPort messageCommandPort;
    
    public void sendMessage(Long senderId, Long receiverId, String content) {
        Message message = Message.create(senderId, receiverId, content);
        messageCommandPort.save(message);
    }

    public void markAsRead(Long senderId, Long receiverId) {
        MessageParticipants participants = MessageParticipants.of(senderId, receiverId);
        messageCommandPort.markAsRead(participants);
    }

    @Transactional
    public void deleteMessage(Long userId, Long messageId) {
        Message message = messageCommandPort.findById(messageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MESSAGE_NOT_FOUND));

        deleteByRole(message, userId);
    }

    private void deleteByRole(Message message, Long userId) {
        Long senderId = message.getMessageParticipants().getSenderId();
        Long receiverId = message.getMessageParticipants().getReceiverId();

        if (userId.equals(senderId)) {
            message.getMessageState().deleteBySender();
            return;
        }

        if (userId.equals(receiverId)) {
            message.getMessageState().deleteByReceiver();
            return;
        }

        throw new BusinessException(ErrorCode.UNAUTHORIZED_ACTION);
    }
}
