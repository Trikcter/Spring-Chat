package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.UserDTO;

import java.util.List;

public interface UserService {
    void addUser(UserDTO user);

    void delete(long id);

    User getByUsername(String username);

    User editUser(User user);

    List<User> getAll();

    User save(User user);
}
