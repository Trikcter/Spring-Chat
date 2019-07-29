package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.Message;

import java.util.List;
import java.util.Optional;

/**
 * @author Bolgov
 *
 * Сервис для работы с сущностью Message
 */
public interface MessageService {
    /**
     * Сохраняет POJO в базу данных
     *
     * @param message - сохраняемое сообщение
     * @return - возвращает Optional для дальнейшней проверки
     */
    Optional<Message> save(Message message);

    /**
     * Удаление из базы данных
     *
     * @param id - индентификатор сообщения в базе данных
     */
    void delete(Long id);

    /**
     *
     * @return - возвращает весь список хранящихся сообщений
     */
    List<Message> getAll();
}
