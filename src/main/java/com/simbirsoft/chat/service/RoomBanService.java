package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.RoomBan;
import com.simbirsoft.chat.entity.User;

import java.util.Date;
import java.util.List;

/**
 * @author Bolgov
 *
 * Сервис для работы с сущностью RoomBan
 * Нужен для выполнения заданий для разбана пользователя в комнате
 */
public interface RoomBanService {
    /**
     * Блокирует доступ к комнате для пользователя
     *
     * @param userBan - блокируемый пользователь
     * @param roomBan - в какой комнате блокируется
     * @param to - на какое время
     */
    void addBan(User userBan, Room roomBan, Date to);

    /**
     * Удаляет блокировку из базы данных
     * @param ban - удаляемая блокировка
     */
    void delete(RoomBan ban);

    /**
     * @return - возвращает список всех блокировок
     */
    List<RoomBan> getAll();
}
