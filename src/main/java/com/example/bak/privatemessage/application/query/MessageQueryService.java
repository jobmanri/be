package com.example.bak.privatemessage.application.query;

import com.example.bak.privatemessage.application.query.dto.MessageCorrespondentResult;
import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.application.query.port.MessageQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageQueryService {

    private final MessageQueryPort messageQueryPort;

    public List<MessageCorrespondentResult> getMessageCorrespondentsByUserId(Long userId) {
        return messageQueryPort.findCorrespondentsByUserId(userId);
    }

    public List<MessageItemResult> getMessagesBetweenUsers(Long fromUserId, Long toUserId) {
        return messageQueryPort.findMessagesBetweenUsers(fromUserId, toUserId);
    }
}
