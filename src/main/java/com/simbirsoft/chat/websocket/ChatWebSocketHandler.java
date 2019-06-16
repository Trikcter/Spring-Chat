package com.simbirsoft.chat.websocket;

import com.simbirsoft.chat.model.Message;
import org.springframework.web.socket.*;

import java.util.HashSet;
import java.util.Set;

public class ChatWebSocketHandler implements WebSocketHandler {

    private Set<WebSocketSession> sessions = new HashSet<>();
    private Set<Message> messages = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessions.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        for(WebSocketSession session : sessions){
            session.sendMessage(new TextMessage(webSocketMessage.getPayload().toString()));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        sessions.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
