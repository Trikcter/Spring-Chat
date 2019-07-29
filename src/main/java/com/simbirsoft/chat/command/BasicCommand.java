package com.simbirsoft.chat.command;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.CommandAttribute;
import com.simbirsoft.chat.model.GenericRs;

/**
 * @author Bolgov
 *
 * Данный интерфейс описывает команду
 */
public interface BasicCommand {
    /**
     * Выполняет команду
     *
     * @param command - атрибуты команды
     * @param user - отправитель команды
     * @return - статус выполнения команды
     */
    GenericRs executeCommand(CommandAttribute command, User user);

    /**
     *
     * @return возвращает название команды
     */
    String getName();
}
