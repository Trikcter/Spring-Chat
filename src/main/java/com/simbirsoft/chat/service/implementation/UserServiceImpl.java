package com.simbirsoft.chat.service.implementation;

import com.simbirsoft.chat.DAO.UserRepository;
import com.simbirsoft.chat.entity.Role;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.UserDTO;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Bolgov
 * Сервис для управления User'ами
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Создание нового пользователя и добавление его в БД
     * @param userDTO - DTO для User, приходящая с фронта
     */
    @Override
    public void addUser(UserDTO userDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode("user"));

        userRepository.save(user);
    }

    /**
     * Удаление пользователя из БД
     * @param username - имя пользователя
     */
    @Override
    public void delete(Long id) {
        User delUser = userRepository.findById(id).orElse(new User());

        userRepository.delete(delUser);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User editUser(String currentName, String futureName) {
        User user = userRepository.findByUsername(currentName).orElse(new User());

        user.setUsername(futureName);

        return userRepository.save(user);
    }

    /**
     * Получаем список всех пользователей в виде DTO для передачи на фронт
     * @return список всех пользователей из БД
     */
    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> answer = new ArrayList<>();

        for(User user: users){
            UserDTO userDTO = new UserDTO();

            userDTO.setActive(user.getActive());
            userDTO.setUsername(user.getUsername());
            userDTO.setId(user.getId());

            List<Role> roles = new ArrayList<>();

            for(Role role : user.getRoles()){
                roles.add(role);
            }

            userDTO.setRoleSet(roles);

            answer.add(userDTO);
        }

        return answer;
    }

    /**
     * Сохраняем пользователя в БД
     * @param user объект, который необходимо сохранить
     * @return возвращаем новый объект в случае успешного сохранения в БД
     */
    @Override
    public User save(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    /**
     * Метод для установки нового модератора
     * @param username имя пользователя
     */
    @Override
    public void makeModerator(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        user.addRole(Role.MODERATOR);

        userRepository.save(user);
    }

    /**
     * Метод для удаления прав администратора
     * @param username имя пользователя
     */
    @Override
    public void removeModerator(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        Set<Role> roleSet = user.getRoles();

        roleSet.remove(Role.MODERATOR);

        user.setRoles(roleSet);

        userRepository.save(user);
    }

    /**
     * Метод для бана пользователя
     * @param username имя пользователя
     */
    @Override
    public void blockUser(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        user.setActive(false);

        userRepository.save(user);
    }

    /**
     * Метод для разбана пользователя
     * @param username - имя пользователя
     */
    @Override
    public void unblockUser(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        user.setActive(true);

        userRepository.save(user);
    }
}
