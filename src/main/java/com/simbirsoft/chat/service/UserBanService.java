package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.UserBan;

import java.util.List;

/**
 * @author Bolgov
 *
 * Сервис, работающий с сущностью UserBan
 * Нужен для блокировки пользователя к приложению
 */
public interface UserBanService {
    /**
     * Добавлет блокировку в базу данных
     * @param userBan - блокируемый пользователь
     */
    void addBan(UserBan userBan);

    /**
     *
     * @return возвращает список всех блокировок
     */
    List<UserBan> getAll();

    /**
     * Удаляет блокировку из базы данных
     * @param userBan - удаляемая блокировка
     */
    void deleteTask(UserBan userBan);
}
