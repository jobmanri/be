package com.example.bak.privatemessage.application.query;

import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.application.query.dto.MessagePartnerResult;
import com.example.bak.privatemessage.application.query.port.MessageQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageQueryService {

    private final MessageQueryPort messageQueryPort;

    public List<MessagePartnerResult> getPartnersByUserId(Long userId) {
        return messageQueryPort.findPartnersByUserId(userId);
    }

    public List<MessageItemResult> getMessagesBetweenUsers(Long fromUserId, Long toUserId) {
        return messageQueryPort.findMessagesBetweenUsers(fromUserId, toUserId);
    }
}
