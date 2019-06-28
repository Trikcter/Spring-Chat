package com.simbirsoft.chat.websocket;

import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.HashSet;
import java.util.Set;

public class ChatWebSocketHandler implements WebSocketHandler {

    private Set<WebSocketSession> sessions = new HashSet<>();
    private Set<Message> messages = new HashSet<>();

    @Autowired
    MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessions.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        for (WebSocketSession session : sessions) {
            String[] mes = webSocketMessage.getPayload().toString().split(":");
            Message message = new Message(mes[0], mes[1]);
            messageService.save(message);

            session.sendMessage(new TextMessage(message.getSocketMessage()));
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
