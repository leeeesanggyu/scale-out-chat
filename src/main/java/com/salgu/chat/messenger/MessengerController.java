package com.salgu.chat.messenger;

import com.salgu.chat.websocket.StompProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessengerController {

    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("main.message")
    public void sendSystemMessage() {
        log.info("sendSystemMessage()");
        rabbitTemplate.convertAndSend(
                "main.topic",
                "main",
                StompProtocol.<String>builder()
                        .command("SYSTEM")
                        .data("점검 10분 전")
                        .build()
        );
    }

    @MessageMapping("group.message")
    public void sendGroupMessage(sendMessageDto sendMessageDto) {
        log.info("sendGroupMessage()");
        rabbitTemplate.convertAndSend(
                "chat.exchange",
                sendMessageDto.getTargetUserId(),
                StompProtocol.<String>builder()
                        .command("DIRECT_MESSAGE")
                        .data("안녕하세요 채팅방 메세지입니다.")
                        .build()
        );
    }

    @MessageMapping("direct.message")
    public void sendDirectMessage(sendMessageDto sendMessageDto) {
        log.info("sendDirectMessage()");
        rabbitTemplate.convertAndSend(
                "chat.direct",
                sendMessageDto.getTargetUserId(),
                StompProtocol.<String>builder()
                        .command("DIRECT_MESSAGE")
                        .data("안녕하세요 다이렉트 메세지입니다.")
                        .build()
        );
    }

    @ToString
    @Getter
    @AllArgsConstructor
    public static class sendMessageDto {
        private String targetUserId;
        private String content;
    }
}
