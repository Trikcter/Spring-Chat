package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.DAO.RoomRepository;
import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.RoomBanService;
import com.simbirsoft.chat.service.RoomService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomBanService roomBanService;

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public Message addMessage(Message message, Room room) {
        List<Message> newMessage = room.getMessages();
        newMessage.add(message);

        room.setMessages(newMessage);

        roomRepository.save(room);

        return message;
    }

    @Override
    public void addParticipants(User user, Room room) {
        Set<User> users = room.getParticipants();

        users.add(user);

        room.setParticipants(users);

        roomRepository.save(room);
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
    public Set<Room> getRoomsByUsername(String username) {
        Set<Room> roomSet = new HashSet<>();

        User user = userService.getByUsername(username).orElse(new User());

        List<Room> rooms = roomRepository.findRoomByOwner(user);

        List<Room> openRooms = roomRepository.findRoomByIsLocked(false);

        List<Room> banRooms = roomRepository.findRoomBybanList(user);

        roomSet.addAll(rooms);
        roomSet.addAll(openRooms);

        for(Room rmRoom : banRooms){
            roomSet.remove(rmRoom);
        }

        return roomSet;
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public void banUser(User user, Room room) {
        Set<User> banList = room.getBanList();

        banList.add(user);

        room.setBanList(banList);
        Set<User> newList = room.getParticipants();

        for(User rmUser : newList){
            if(rmUser.getUsername().equals(user.getUsername())){
                newList.remove(rmUser);
            }
        }

        room.setParticipants(newList);

        roomRepository.save(room);
    }

    @Override
    public void unbanUser(User user, Room room) {
        Set<User> banList = room.getBanList();

        for(User rmUser : banList){
            if(rmUser.getUsername().equals(user.getUsername())){
                banList.remove(rmUser);
            }
        }

        room.setBanList(banList);

        roomRepository.save(room);
    }

    @Override
    public Room getActiveRoom(String username) {
        User user = userService.getByUsername(username).orElse(new User());

        Room room = roomRepository.findFirstRoomByOwner(user).orElse(new Room());

        return room;
    }
}
