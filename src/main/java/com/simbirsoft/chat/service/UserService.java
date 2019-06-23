package com.simbirsoft.chat.service;

import com.simbirsoft.chat.model.Users;

import java.util.List;

public interface UserService {
    void addUser(Users user);

    void delete(long id);

    Users getByusername(String username);

    Users editUser(Users user);

    List<Users> getAll();
}
