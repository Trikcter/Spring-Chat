package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.GenericRs;

/**
 * @author Bolgov
 *
 * Сервис для выполнения команд
 */
public interface CommandService {
    /**
     * Выполняет команду
     *
     * @param command - вся команда целиком
     * @param user - отправитель команды
     * @return - статус выполнения
     */
    GenericRs executeCommand(String command, User user);
}
