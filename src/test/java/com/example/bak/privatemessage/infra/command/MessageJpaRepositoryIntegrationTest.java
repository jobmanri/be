package com.example.bak.privatemessage.infra.command;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.global.AbstractMySqlContainerTest;
import com.example.bak.privatemessage.domain.Message;
import com.example.bak.privatemessage.domain.MessageParticipants;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@Slf4j
@DataJpaTest
@Sql(scripts = "/sql/message/data.sql")
@DisplayName("MessageJpaRepository 통합 테스트")
class MessageJpaRepositoryIntegrationTest extends AbstractMySqlContainerTest {

    @Autowired
    MessageJpaRepository messageJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("특정 발신·수신자 조합의 읽지 않은 메시지를 모두 읽음 처리한다")
    void test_mark_messages_as_read() {
        // given
        MessageParticipants participants = MessageParticipants.of(1L, 2L);

        List<Message> beforeMessages = findMessages(participants);
        assertThat(beforeMessages)
                .isNotEmpty()
                .allMatch(msg -> msg.getMessageState().getReadAt() == null);

        // when
        int updatedCount = messageJpaRepository.markAsReadByParticipants(
                participants.getSenderId(),
                participants.getReceiverId()
        );
        em.flush();
        em.clear();

        // then
        assertThat(updatedCount).isEqualTo(beforeMessages.size());

        List<Message> afterMessages = findMessages(participants);
        assertThat(afterMessages)
                .isNotEmpty()
                .allMatch(msg -> msg.getMessageState().getReadAt() != null);
    }

    private List<Message> findMessages(MessageParticipants participants) {
        return em.createQuery("""
                        SELECT m FROM private_messages m
                         WHERE m.messageParticipants.senderId = :senderId
                           AND m.messageParticipants.receiverId = :receiverId
                        """, Message.class)
                .setParameter("senderId", participants.getSenderId())
                .setParameter("receiverId", participants.getReceiverId())
                .getResultList();
    }
}
