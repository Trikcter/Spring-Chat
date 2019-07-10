package com.simbirsoft.chat.service;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(UserDTO user);

    void delete(Long id);

    Optional<User> getByUsername(String username);

    User editUser(String currentName, String futureName);

    List<UserDTO> getAll();

    User save(User user);

    void makeModerator(Long id);

    void removeModerator(Long id);

    void blockUser(Long id);

    void unblockUser(Long id);
}
