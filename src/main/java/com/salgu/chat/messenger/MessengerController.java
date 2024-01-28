package com.salgu.chat.messenger;

import com.salgu.chat.websocket.StompProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessengerController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/send/message")
    public String sendMessage(sendMessageDto sendMessageDto) {
        messagingTemplate.convertAndSend(
                "/sub/messenger/" + sendMessageDto.getTargetUserId(),
                StompProtocol.<String>builder()
                        .command("MESSAGE")
                        .data(sendMessageDto.getContent())
                        .build()
        );
        return "success";
    }

    @Getter
    @AllArgsConstructor
    public static class sendMessageDto {
        private String targetUserId;
        private String content;
    }
}
