package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.UserDTO;

import java.util.List;

public interface UserService {
    void addUser(UserDTO user);

    void delete(String username);

    User getByUsername(String username);

    User editUser(User user);

    List<UserDTO> getAll();

    User save(User user);

    void makeModerator(String username);

    void removeModerator(String username);

    void blockUser(String username);

    void unBlockUser(String username);
}
