package com.simbirsoft.chat.DAO;

import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    Optional<Room> findByName(String name);
    /*@Query("Select * from room where is_locked = false")
    List<Room> findByNameNonLocked(String name);*/
    List<Room> findRoomByOwner(User user);
}
