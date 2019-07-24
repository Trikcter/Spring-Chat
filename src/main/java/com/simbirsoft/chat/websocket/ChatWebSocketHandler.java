package com.simbirsoft.chat.websocket;

import com.google.gson.Gson;
import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.exceptions.MessageNotSaveException;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.model.MessageDTO;
import com.simbirsoft.chat.service.CommandService;
import com.simbirsoft.chat.service.MessageService;
import com.simbirsoft.chat.service.RoomService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.*;

public class ChatWebSocketHandler implements WebSocketHandler {
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommandService commandService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    private HashMap<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        User user = userService.getByUsername(webSocketSession.getPrincipal().getName()).orElseThrow(Exception::new);
        sessions.put(user.getUsername(), webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        Gson g = new Gson();

        MessageDTO messageDTO = g.fromJson(webSocketMessage.getPayload().toString(), MessageDTO.class);
        String username = webSocketSession.getPrincipal().getName();

        User user = userService.getByUsername(username).orElseThrow((Exception::new));

        Room activeRoom = roomService.getRoomByName(messageDTO.getTo()).orElseThrow(Exception::new);

        MessageDTO frontMessage = getUIMessage(messageDTO, user);

        String json = g.toJson(frontMessage);

        if (!("text".equals(frontMessage.getTypeOfMessage()) && !("".equals(frontMessage.getTo())))) {
            webSocketSession.sendMessage(new TextMessage(json));

            return;
        }

        Set<User> participants = activeRoom.getParticipants();

        for(User takeUser : participants){
            sessions.get(takeUser.getUsername()).sendMessage(new TextMessage(json));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        sessions.values().remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private MessageDTO getUIMessage(MessageDTO message, User user) throws Exception {
        String text = message.getMessage();
        Long id = message.getId();

        MessageDTO frontMessage = new MessageDTO();

        frontMessage.setFrom(user.getUsername());
        frontMessage.setId(id);

        if (text.contains("//")) {
            StringBuilder command = new StringBuilder(text);
            command.delete(0, 2);
            text = command.toString();

            GenericRs answer = commandService.executeCommand(text, user);

            frontMessage.setMessage(answer.getMessage()[0]);
            frontMessage.setTypeOfMessage(answer.getStatus());
            frontMessage.setTo(message.getTo());

           /* if (answer.getStatus().equals("Connect")) {
                Optional<Room> room = roomService.getRoomByUser(user);

                if (room.isPresent()) {
                    userRoom.get(room.get().getName()).add(user);
                }

                frontMessage.setMessage(messageSource.getMessage("success.openConnection", new Object[0], Locale.getDefault()));
            }

            if (answer.getStatus().equals("Create")) {
                userRoom.put(answer.getMessage()[0], new HashSet<>());
                frontMessage.setMessage(messageSource.getMessage("success.createRoom", new Object[0], Locale.getDefault()));
            }*/

        } else {
            saveMessage(frontMessage,user);

            frontMessage.setMessage(text);
            frontMessage.setTypeOfMessage("text");
        }

        return frontMessage;
    }

    private void saveMessage(MessageDTO messageDTO, User user) throws Exception {
        Message message = new Message();

        message.setMessage(messageDTO.getMessage());
        message.setAuthor(user);

        messageService.save(message).orElseThrow(() -> new MessageNotSaveException("Ошибка сохранения в БД"));
    }

}
