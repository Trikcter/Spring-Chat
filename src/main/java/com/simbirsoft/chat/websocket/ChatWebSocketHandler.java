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

        Optional<Room> room = roomService.getRoomByName(messageDTO.getTo());

        MessageDTO frontMessage = getUIMessage(messageDTO, user,room);

        String json = g.toJson(frontMessage);

        if (!("text".equals(frontMessage.getTypeOfMessage()))) {
            webSocketSession.sendMessage(new TextMessage(json));

            return;
        }

        if(room.isPresent()){
            Room activeRoom = room.get();

            Set<User> participants = activeRoom.getParticipants();

            for(User takeUser : participants){
                if(sessions.keySet().contains(takeUser.getUsername())) {
                    sessions.get(takeUser.getUsername()).sendMessage(new TextMessage(json));
                }
            }

            return;
        }

        for(WebSocketSession session : sessions.values()){
            session.sendMessage(new TextMessage(json));
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

    private MessageDTO getUIMessage(MessageDTO message, User user, Optional<Room> currentRoom) throws Exception {
        String text = message.getMessage();

        MessageDTO frontMessage = new MessageDTO();

        frontMessage.setFrom(user.getUsername());

        if (text.contains("//")) {
            StringBuilder command = new StringBuilder(text);
            command.delete(0, 2);
            text = command.toString();

            GenericRs answer = commandService.executeCommand(text, user);

            frontMessage.setMessage(answer.getMessage()[0]);
            frontMessage.setTypeOfMessage(answer.getStatus());
            frontMessage.setTo(message.getTo());

            if (answer.getStatus().equals("Connect")) {
                Room room = roomService.getRoomByName(answer.getMessage()[0]).orElse(new Room());
                List<Message> hMessage = room.getMessages();

                frontMessage.setTypeOfMessage("Connect");
                frontMessage.setTo(answer.getMessage()[0]);
                frontMessage.setHistoryOfMessage(hMessage);
            }

        } else {
            frontMessage.setMessage(text);
            frontMessage.setTypeOfMessage("text");

            Long id = saveMessage(frontMessage,user,currentRoom);

            frontMessage.setId(id);
        }

        return frontMessage;
    }

    private Long saveMessage(MessageDTO messageDTO, User user,Optional<Room> room) throws Exception {
        Message message = new Message();

        message.setMessage(messageDTO.getMessage());
        message.setAuthor(user);

        Message savedMessage = messageService.save(message).orElseThrow(() -> new MessageNotSaveException("Ошибка сохранения в БД"));

        if(room.isPresent()){
            Room saveRoom = room.get();
            roomService.addMessage(savedMessage,saveRoom);
        }

        return savedMessage.getId();
    }

}
