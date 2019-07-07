package com.simbirsoft.chat.websocket;

import com.google.gson.Gson;
import com.simbirsoft.chat.MessageNotSaveException;
import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.model.MessageDTO;
import com.simbirsoft.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.HashSet;
import java.util.Set;

public class ChatWebSocketHandler implements WebSocketHandler {

    private Set<WebSocketSession> sessions = new HashSet<>();
    private Set<String> users = new HashSet<>();

    @Autowired
    MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessions.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        Gson g = new Gson();

        Message message = g.fromJson(webSocketMessage.getPayload().toString(), Message.class);

        messageService.save(message).orElseThrow(() -> new MessageNotSaveException("Ошибка сохранения в БД"));

        String text = message.getMessage();
        String username = message.getUsername();
        Long id = message.getId();

        MessageDTO frontMessage = new MessageDTO();

        frontMessage.setUsername(username);
        frontMessage.setId(id);

        if(text.contains("//")){
            StringBuilder command = new StringBuilder(text);
            command.delete(0,2);
            text = command.toString();

            frontMessage.setMessage(text);
            frontMessage.setTypeOfMessage("command");

            String json = g.toJson(frontMessage);

            webSocketSession.sendMessage(new TextMessage(json));
        }else{
            frontMessage.setMessage(text);
            frontMessage.setTypeOfMessage("text");
            String json = g.toJson(frontMessage);

            for (WebSocketSession session : sessions) {
                session.sendMessage(new TextMessage(json));
            }
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
