package com.salgu.chat.websocket;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StompProtocol<T> {

    private String command;
    private T data;

    @Builder
    private StompProtocol(String command, T data) {
        this.command = command;
        this.data = data;
    }
}
