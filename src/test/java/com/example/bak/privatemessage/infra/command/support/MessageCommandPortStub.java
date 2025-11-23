package com.example.bak.privatemessage.infra.command.support;

import com.example.bak.global.support.AbstractStubRepository;
import com.example.bak.privatemessage.application.command.port.MessageCommandPort;
import com.example.bak.privatemessage.domain.Message;
import com.example.bak.privatemessage.domain.MessageParticipants;
import java.util.Objects;

public class MessageCommandPortStub
        extends AbstractStubRepository<Long, Message>
        implements MessageCommandPort {

    @Override
    protected Long getId(Message message) {
        return message.getId();
    }

    @Override
    protected boolean isSame(Long left, Long right) {
        return Objects.equals(left, right);
    }

    @Override
    public void markAsRead(MessageParticipants participants) {
        Long senderId = participants.getSenderId();
        Long receiverId = participants.getReceiverId();

        for (Message message : store) {
            boolean isTarget =
                    message.getMessageParticipants().getSenderId().equals(senderId) &&
                            message.getMessageParticipants().getReceiverId().equals(receiverId);

            boolean unread =
                    message.getMessageState().getReadAt() == null;

            if (isTarget && unread) {
                message.getMessageState().markAsRead();
            }
        }
    }
}
