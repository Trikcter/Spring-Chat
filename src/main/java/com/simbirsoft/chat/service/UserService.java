package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * @author Bolgov
 *
 * Сервис, работающий с сущностью User
 */
public interface UserService {
    /**
     * Добавляет пользователя с фронта
     * @param user - пользователь, приходящий с фронта
     */
    void addUser(UserDTO user);

    /**
     * Удаляет пользователя
     * @param id - идентификатор пользователя
     */
    void delete(Long id);

    /**
     * @param username - пользователь
     * @return - возвращает пользователь по логину
     */
    Optional<User> getByUsername(String username);

    /**
     * Изменение имени пользователя
     * @param currentName - текущее имя
     * @param futureName - будущее имя
     * @return - новая сущность в базе данных
     */
    User editUser(String currentName, String futureName);

    /**
     *
     * @return - список всех пользователей
     */
    List<UserDTO> getAll();

    /**
     * Сохраняет в базу данных пользователя
     * @param user - сохраняемый пользователь
     * @return - возвращает новую сущность в базе данных
     */
    User save(User user);

    /**
     * Делает пользователя модератором
     * @param id - идентификатор пользователя
     */
    void makeModerator(Long id);

    /**
     * Удаляет пользователя из группы модераторов
     * @param id - идентификатор пользователя
     */
    void removeModerator(Long id);

    /**
     * Банит пользователя
     * @param id - идентификатор пользователя
     */
    void blockUser(Long id);

    /**
     * Разблокирует пользователя
     * @param id - идентификатор пользователя
     */
    void unblockUser(Long id);

    /**
     * Удаляет пользователя из всех комнат
     * @param user - искомый пользователь
     */
    void deleteFromAllRooms(User user);
}
