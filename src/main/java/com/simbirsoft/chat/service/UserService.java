package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void delete(long id);

    User getByUsername(String username);

    User editUser(User user);

    List<User> getAll();

    User save(User user);
}
