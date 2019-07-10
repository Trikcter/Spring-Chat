package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.DAO.RoomRepository;
import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.RoomService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserService userService;

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public void addMessage(Message message) {

    }

    @Override
    public void addParticipants(User user) {

    }

    @Override
    public Optional<Room> getRoomByName(String name) {
        return roomRepository.findByName(name);
    }

    @Override
    public void edit(Room room) {
        roomRepository.save(room);
    }

    @Override
    public List<Room> getRoomsByUsername(String username) {
        List<Room> rooms = new ArrayList<>();

        User user = userService.getByUsername(username).orElse(new User());

        rooms = roomRepository.findRoomByOwner(user);

        return rooms;
    }
}
