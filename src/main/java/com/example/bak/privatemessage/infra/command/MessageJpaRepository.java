package com.example.bak.privatemessage.infra.command;

import com.example.bak.privatemessage.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {

    @Modifying(clearAutomatically = true)
    @Query("""
                update private_messages m
                set m.messageState.readAt = CURRENT_TIMESTAMP
                where m.messageParticipants.receiverId = :receiverId
                  and m.messageParticipants.senderId = :senderId
                  and m.messageState.readAt is null
            """)
    int markAsReadByParticipants(
            @Param("senderId") Long senderId,
            @Param("receiverId") Long receiverId
    );
}
