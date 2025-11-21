package com.example.bak.privatemessage.infra.query;

import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.application.query.dto.MessagePartnerResult;
import com.example.bak.privatemessage.application.query.port.MessageQueryPort;
import com.example.bak.privatemessage.infra.query.jdbc.MessageJdbcRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageQueryAdapter implements MessageQueryPort {

    private final MessageJdbcRepository messageJdbcRepository;

    @Override
    public List<MessagePartnerResult> findPartnersByUserId(Long userId) {
        return messageJdbcRepository.findPartnersByUserId(userId);
    }

    @Override
    public List<MessageItemResult> findMessagesBetweenUsers(Long fromUserId, Long toUserId) {
        return messageJdbcRepository.findMessagesBetweenUsers(fromUserId, toUserId);
    }
}