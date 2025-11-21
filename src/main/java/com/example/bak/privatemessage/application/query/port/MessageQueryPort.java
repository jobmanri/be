package com.example.bak.privatemessage.application.query.port;

import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.application.query.dto.MessagePartnerResult;
import java.util.List;

public interface MessageQueryPort {

    List<MessagePartnerResult> findPartnersByUserId(Long userId);

    List<MessageItemResult> findMessagesBetweenUsers(Long fromUserId, Long toUserId);
}
