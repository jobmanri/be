package com.example.bak.privatemessage.presentation.swagger;

import com.example.bak.global.common.response.ApiResponse;
import com.example.bak.privatemessage.presentation.MessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "PrivateMessage", description = "쪽지 관리 API")
public interface MessageSwagger {

    @Operation(
            summary = "쪽지 전송",
            description = "특정 사용자에게 쪽지를 전송합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "쪽지 전송 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "쪽지를 성공적으로 전송했습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "수신자를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "수신자를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> sendMessage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "쪽지 전송 요청 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = MessageRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "senderId": 1,
                                                "receiverId": 2,
                                                "content": "안녕하세요! 궁금한 점이 있어서 쪽지 드립니다."
                                            }
                                            """
                            )
                    )
            )
            @RequestBody MessageRequest request
    );

    @Operation(
            summary = "쪽지 대화 상대 목록 조회",
            description = "특정 사용자와 쪽지를 주고받은 모든 대화 상대 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "대화 상대 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "쪽지 대화 상대 목록을 조회하였습니다.",
                                                "data": [
                                                    {
                                                        "userId": 2,
                                                        "userName": "김철수",
                                                        "lastMessage": "감사합니다!",
                                                        "lastMessageTime": "2024-01-15T14:30:00",
                                                        "unreadCount": 3
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "사용자를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getParticipants(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @RequestParam Long userId
    );

    @Operation(
            summary = "두 사용자 간 쪽지 내용 조회",
            description = "두 사용자 간에 주고받은 모든 쪽지 내용을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "쪽지 내용 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "쪽지 내용들을 조회하였습니다.",
                                                "data": [
                                                    {
                                                        "messageId": 1,
                                                        "senderId": 1,
                                                        "senderName": "홍길동",
                                                        "receiverId": 2,
                                                        "receiverName": "김철수",
                                                        "content": "안녕하세요!",
                                                        "isRead": true,
                                                        "createdAt": "2024-01-15T14:00:00"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> getMessagesBetweenUsers(
            @Parameter(description = "조회할 사용자 ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "대화 상대 ID", required = true, example = "2")
            @RequestParam Long participantId
    );

    @Operation(
            summary = "쪽지 읽음 처리",
            description = "특정 사용자로부터 받은 쪽지를 모두 읽음 처리합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "읽음 처리 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "읽음 처리되었습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> markAsRead(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "대화 상대 ID", required = true, example = "2")
            @RequestParam Long participantId
    );

    @Operation(
            summary = "쪽지 삭제",
            description = "특정 쪽지를 삭제합니다. 본인이 보낸 쪽지만 삭제 가능합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "쪽지 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "SUCCESS",
                                                "message": "쪽지를 삭제했습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "권한 없음 - 본인의 쪽지만 삭제 가능",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "쪽지를 삭제할 권한이 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "쪽지를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "message": "쪽지를 찾을 수 없습니다.",
                                                "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ApiResponse> deleteMessage(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "쪽지 ID", required = true, example = "1")
            @PathVariable Long messageId
    );
}
