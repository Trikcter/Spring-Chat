package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class CommandController {
    @Autowired
    private UserService userService;

    @GetMapping("/rename/{username}")
    @ResponseBody
    public String rename(Authentication authentication, @PathVariable String username){
        User user = userService.editUser(authentication.getName(),username);
        return user.getUsername();
    }
}
