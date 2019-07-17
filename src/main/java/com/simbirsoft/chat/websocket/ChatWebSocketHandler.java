package com.simbirsoft.chat.websocket;

import com.google.gson.Gson;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.exceptions.MessageNotSaveException;
import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.model.MessageDTO;
import com.simbirsoft.chat.service.CommandService;
import com.simbirsoft.chat.service.MessageService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.HashSet;
import java.util.Set;

public class ChatWebSocketHandler implements WebSocketHandler {
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommandService commandService;
    @Autowired
    private UserService userService;

    private Set<WebSocketSession> sessions = new HashSet<>();
    private Set<String> users = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessions.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        Gson g = new Gson();

        MessageDTO messageDTO = g.fromJson(webSocketMessage.getPayload().toString(), MessageDTO.class);
        String username = webSocketSession.getPrincipal().getName().toString();

        Message message = saveMessage(messageDTO,username);

        MessageDTO frontMessage = getUIMessage(message,username);

        if (!("text".equals(frontMessage.getTypeOfMessage()))) {
            String json = g.toJson(frontMessage);

            webSocketSession.sendMessage(new TextMessage(json));
        } else {
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

    private MessageDTO getUIMessage(Message message, String username) throws Exception {
        String text = message.getMessage();
        User user = userService.getByUsername(username).orElseThrow(() -> new Exception());
        Long id = message.getId();

        MessageDTO frontMessage = new MessageDTO();

        frontMessage.setUsername(username);
        frontMessage.setId(id);

        if (text.contains("//")) {
            StringBuilder command = new StringBuilder(text);
            command.delete(0, 2);
            text = command.toString();

            GenericRs answer = commandService.executeCommand(text,user);

            frontMessage.setMessage(answer.getMessage()[0]);
            frontMessage.setTypeOfMessage(answer.getStatus());
        }else{
            frontMessage.setMessage(text);
            frontMessage.setTypeOfMessage("text");
        }

        return frontMessage;
    }

    private Message saveMessage(MessageDTO messageDTO, String username) throws Exception {

        Message message = new Message();

        message.setMessage(messageDTO.getMessage());

        User saveUser = userService.getByUsername(username).orElseThrow(() -> new Exception());
        message.setAuthor(saveUser);

        Message messageReturn = messageService.save(message).orElseThrow(() -> new MessageNotSaveException("Ошибка сохранения в БД"));

        return messageReturn;
    }
}
