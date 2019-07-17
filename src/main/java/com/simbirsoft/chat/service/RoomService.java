package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoomService {
    Room addRoom(Room room);

    void delete(Room room);

    void addMessage(Message message);

    void addParticipants(User user, Room room);

    Optional<Room> getRoomByName(String name);

    void edit(Room room);

    Set<Room> getRoomsByUsername(String username);
}
