package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Room;

public interface RoomService {
    Room addRoom(Room room);

    void delete(Room room);
}
