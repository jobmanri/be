package com.example.bak.privatemessage.application.command;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.bak.global.exception.ErrorCode;
import com.example.bak.privatemessage.domain.Message;
import com.example.bak.privatemessage.infra.command.support.MessageCommandPortStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MessageCommandService 단위 테스트")
class MessageCommandServiceUnitTest {

    private MessageCommandService service;
    private MessageCommandPortStub port;

    @BeforeEach
    void setUp() {
        port = new MessageCommandPortStub();
        service = new MessageCommandService(port);
    }

    private Message setupMessage(Long senderId, Long receiverId, String content) {
        Message message = Message.create(senderId, receiverId, content);
        return port.save(message);
    }

    @Nested
    @DisplayName("sendMessage")
    class SendMessageTest {

        @Test
        @DisplayName("쪽지 전송에 성공한다")
        void sendMessage_success() {
            service.sendMessage(1L, 2L, "hello");

            Message saved = port.findAll().getFirst();
            assertThat(saved.getMessageParticipants().getSenderId()).isEqualTo(1L);
            assertThat(saved.getMessageParticipants().getReceiverId()).isEqualTo(2L);
            assertThat(saved.getMessageState().getReadAt()).isNull();
        }
    }

    @Nested
    @DisplayName("markAsRead")
    class MarkAsReadTest {

        @Test
        @DisplayName("특정 사용자 간 메시지를 모두 읽음 처리한다")
        void markAsRead_success() {
            Message m1 = setupMessage(1L, 2L, "a");
            Message m2 = setupMessage(1L, 2L, "b");
            Message m3 = setupMessage(2L, 1L, "c");

            service.markAsRead(1L, 2L);

            assertThat(m1.getMessageState().getReadAt()).isNotNull();
            assertThat(m2.getMessageState().getReadAt()).isNotNull();
            assertThat(m3.getMessageState().getReadAt()).isNull();
        }
    }

    @Nested
    @DisplayName("deleteMessage")
    class DeleteMessageTest {

        @Test
        @DisplayName("보낸 사람이 메시지를 삭제할 수 있다")
        void deleteMessage_sender_success() {
            Message msg = setupMessage(1L, 2L, "hi");

            service.deleteMessage(1L, msg.getId());

            assertThat(msg.getMessageState().getDeletedAtBySender()).isNotNull();
            assertThat(msg.getMessageState().getDeletedAtByReceiver()).isNull();
        }

        @Test
        @DisplayName("받는 사람이 메시지를 삭제할 수 있다")
        void deleteMessage_receiver_success() {
            Message msg = setupMessage(1L, 2L, "hi");

            service.deleteMessage(2L, msg.getId());

            assertThat(msg.getMessageState().getDeletedAtBySender()).isNull();
            assertThat(msg.getMessageState().getDeletedAtByReceiver()).isNotNull();
        }

        @Test
        @DisplayName("존재하지 않는 메시지는 예외를 던진다")
        void deleteMessage_notFound() {
            assertBusiness(
                    () -> service.deleteMessage(1L, 999L),
                    ErrorCode.MESSAGE_NOT_FOUND
            );
        }

        @Test
        @DisplayName("메시지의 sender와 receiver 모두가 아닌 사용자는 삭제할 수 없다")
        void deleteMessage_unauthorized() {
            Message msg = setupMessage(1L, 2L, "hi");

            assertBusiness(
                    () -> service.deleteMessage(999L, msg.getId()),
                    ErrorCode.UNAUTHORIZED_ACTION
            );
        }
    }
}