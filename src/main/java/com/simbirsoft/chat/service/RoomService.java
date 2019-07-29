package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Message;
import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Bolgov
 *
 * Сервис для взаимодействия с сущностью Room
 */
public interface RoomService {
    /**
     * Добавляет новую комнату в базу данных
     * @param room - добавляемая комната
     * @return - возвращаемый объект из базы данных
     */
    Room addRoom(Room room);

    /**
     * Удаляет комнату из базы данных
     * @param room - удаляемая комната
     */
    void delete(Room room);

    /**
     * Добавляет сообщение в историю сообщений комнаты
     * @param message - добавляемое сообщение
     * @param room - комната, в которой обновляется история сообщений
     * @return - возвращаемое из базы данных значение
     */
    Message addMessage(Message message, Room room);

    /**
     * Добавляет нового участника в комнату
     * @param user - новый участник
     * @param room - комната, к которой надо добавить участника
     */
    void addParticipants(User user, Room room);

    /**
     * @param name - название искомой комнаты
     * @return найденную комнату
     */
    Optional<Room> getRoomByName(String name);

    /**
     * Редактирование существующей комнаты
     * @param room - редактируемая комната
     */
    void edit(Room room);

    /**
     * @param username - пользователь
     * @return - все комнаты, в которых состоит пользователь
     */
    Set<Room> getRoomsByUsername(String username);

    /**
     * @return - список всех комнат
     */
    List<Room> getAll();

    /**
     * Добавляет пользователь в бан-лист
     * @param user - блокируемый пользователь
     * @param room - комната, в которой надо обновить бан-лист
     */
    void banUser(User user, Room room);

    /**
     * Удаляет пользователя из бан-листа
     * @param user - разблокируемый пользователь
     * @param room - комната, в которой надо обновить бан-лист
     */
    void unbanUser(User user,Room room);

    /**
     * @param username - пользователь
     * @return - возвращает активную комнату пользователя
     */
    Room getActiveRoom(String username);
}
