package com.simbirsoft.chat.websocket;

import com.google.gson.Gson;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.exceptions.MessageNotSaveException;
import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.model.GenericRs;
import com.simbirsoft.chat.model.MessageDTO;
import com.simbirsoft.chat.service.CommandService;
import com.simbirsoft.chat.service.MessageService;
import com.simbirsoft.chat.service.RoomService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.socket.*;

import javax.annotation.PostConstruct;
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
    @Autowired
    private MessageSource messageSource;

    private HashMap<String, WebSocketSession> sessions = new HashMap<>();
    private WeakHashMap<String, Set<User>> userRoom = new WeakHashMap<>();

    @PostConstruct
    private void loadRooms() {
        List<Room> rooms = roomService.getAll();

        for (Room room : rooms) {
            userRoom.put(room.getName(), room.getParticipants());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        User user = userService.getByUsername(webSocketSession.getPrincipal().getName().toString()).orElseThrow(Exception::new);
        sessions.put(user.getUsername(), webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        Gson g = new Gson();

        MessageDTO messageDTO = g.fromJson(webSocketMessage.getPayload().toString(), MessageDTO.class);
        String username = webSocketSession.getPrincipal().getName().toString();

        Message message = saveMessage(messageDTO, username);
        User user = userService.getByUsername(username).orElseThrow((Exception::new));

        MessageDTO frontMessage = getUIMessage(message, username);

        Optional<Room> room = roomService.getRoomByUser(user);

        if (!("text".equals(frontMessage.getTypeOfMessage()))) {
            String json = g.toJson(frontMessage);

            webSocketSession.sendMessage(new TextMessage(json));
        } else {
            if (room.isPresent()) {
                Set<User> users = userRoom.get(room.get().getName());

                saveMessage(message, room.get());

                String json = g.toJson(frontMessage);
                for (User u : users) {
                    WebSocketSession session = sessions.get(u.getUsername());

                    if (session != null) {
                        session.sendMessage(new TextMessage(json));
                    }
                }
            } else {
                String json = g.toJson(frontMessage);

                Set<WebSocketSession> sessionList = sendToMainPage();

                for (WebSocketSession session : sessionList) {
                    session.sendMessage(new TextMessage(json));
                }
            }
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

    private MessageDTO getUIMessage(Message message, String username) throws Exception {
        String text = message.getMessage();
        User user = userService.getByUsername(username).orElseThrow(Exception::new);
        Long id = message.getId();

        MessageDTO frontMessage = new MessageDTO();

        frontMessage.setUsername(username);
        frontMessage.setId(id);

        if (text.contains("//")) {
            StringBuilder command = new StringBuilder(text);
            command.delete(0, 2);
            text = command.toString();

            GenericRs answer = commandService.executeCommand(text, user);

            frontMessage.setMessage(answer.getMessage()[0]);
            frontMessage.setTypeOfMessage(answer.getStatus());

            if (answer.getStatus().equals("Connect")) {
                Optional<Room> room = roomService.getRoomByUser(user);

                if (room.isPresent()) {
                    userRoom.get(room.get().getName()).add(user);
                }

                frontMessage.setMessage(messageSource.getMessage("success.openConnection", new Object[0], Locale.getDefault()));
            }

            if (answer.getStatus().equals("Create")) {
                userRoom.put(answer.getMessage()[0], new HashSet<>());
                frontMessage.setMessage(messageSource.getMessage("success.createRoom", new Object[0], Locale.getDefault()));
            }

        } else {
            frontMessage.setMessage(text);
            frontMessage.setTypeOfMessage("text");
        }

        return frontMessage;
    }

    private Message saveMessage(MessageDTO messageDTO, String username) throws Exception {

        Message message = new Message();

        message.setMessage(messageDTO.getMessage());

        User saveUser = userService.getByUsername(username).orElseThrow(Exception::new);
        message.setAuthor(saveUser);

        Message messageReturn = messageService.save(message).orElseThrow(() -> new MessageNotSaveException("Ошибка сохранения в БД"));

        return messageReturn;
    }

    private Message saveMessage(Message message, Room room) {
        Message messageReturn = roomService.addMessage(message, room);

        return messageReturn;
    }

    private Set<WebSocketSession> sendToMainPage() {
        Set<WebSocketSession> socketSessions = new HashSet<>();

        for (WebSocketSession session : sessions.values()) {
            socketSessions.add(session);
        }

        for (String room : userRoom.keySet()) {
            for (User user : userRoom.get(room)) {
                WebSocketSession rmSession = sessions.get(user.getUsername());
                socketSessions.remove(rmSession);
            }
        }

        return socketSessions;
    }
}
