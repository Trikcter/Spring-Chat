package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Bolgov
 * Repository для работы с сущностью Room
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);

    List<Room> findRoomByOwner(User user);

    List<Room> findRoomByIsLocked(Boolean isLocked);

    List<Room> findRoomBybanList(User user);

    Optional<Room> findFirstRoomByOwner(User user);
}
