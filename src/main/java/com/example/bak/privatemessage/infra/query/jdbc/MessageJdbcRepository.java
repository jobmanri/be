package com.example.bak.privatemessage.infra.query.jdbc;

import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import com.example.bak.privatemessage.application.query.dto.MessagePartnerResult;
import java.util.List;

public interface MessageJdbcRepository {

    List<MessagePartnerResult> findPartnersByUserId(Long userId);

    List<MessageItemResult> findMessagesBetweenUsers(Long fromUserId, Long toUserId);
}
