package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);

    List<Room> findRoomByOwner(User user);

    List<Room> findRoomByIsLocked(Boolean isLocked);

    Optional<Room> findRoomByParticipants(User user);
}
