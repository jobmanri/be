package com.example.bak.privatemessage.infra.query.jdbc;

import com.example.bak.privatemessage.application.query.dto.MessageCorrespondentResult;
import com.example.bak.privatemessage.application.query.dto.MessageItemResult;
import java.util.List;

public interface MessageJdbcRepository {

    List<MessageCorrespondentResult> findCorrespondentsByUserId(Long userId);

    List<MessageItemResult> findMessagesBetweenUsers(Long fromUserId, Long toUserId);
}
