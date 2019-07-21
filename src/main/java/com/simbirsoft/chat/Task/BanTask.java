package com.simbirsoft.chat.Task;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BanTask implements Runnable {
    @Autowired
    private UserService userService;

    private String username;

    public BanTask(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        Optional<User> user = userService.getByUsername(username);

        if (user.isPresent()) {
            User unBan = user.get();
            unBan.setActive(true);
            userService.save(unBan);
        }
    }
}
