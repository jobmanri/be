package com.example.bak.privatemessage.presentation;

import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.global.common.response.ApiResponseFactory;
import com.example.bak.privatemessage.application.command.MessageCommandService;
import com.example.bak.privatemessage.application.query.MessageQueryService;
import com.example.bak.privatemessage.presentation.swagger.MessageSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private-messages")
@RequiredArgsConstructor
public class MessageController implements MessageSwagger {

    private final MessageCommandService commandService;
    private final MessageQueryService queryService;

    @PostMapping()
    public ResponseEntity<ApiResponse> sendMessage(
            @RequestBody MessageRequest request
    ) {
        commandService.sendMessage(
                request.senderId(),
                request.receiverId(),
                request.content()
        );

        ApiResponse response = ApiResponseFactory.successVoid("쪽지를 성공적으로 전송했습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/participants")
    public ResponseEntity<ApiResponse> getParticipants(@RequestParam Long userId) {
        var result = queryService.getMessageCorrespondentsByUserId(userId);

        ApiResponse response = ApiResponseFactory.success("쪽지 대화 상대 목록을 조회하였습니다.", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getMessagesBetweenUsers(
            @RequestParam Long userId,
            @RequestParam Long participantId
    ) {
        var result = queryService.getMessagesBetweenUsers(userId, participantId);

        ApiResponse response = ApiResponseFactory.success("쪽지 내용들을 조회하였습니다.", result);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/read")
    public ResponseEntity<ApiResponse> markAsRead(
            @RequestParam Long userId,
            @RequestParam Long participantId
    ) {
        commandService.markAsRead(userId, participantId);

        ApiResponse response = ApiResponseFactory.successVoid("읽음 처리되었습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(
            @RequestParam Long userId,
            @PathVariable Long messageId
    ) {
        commandService.deleteMessage(userId, messageId);

        ApiResponse response = ApiResponseFactory.successVoid("쪽지를 삭제했습니다.");
        return ResponseEntity.ok(response);
    }
}
