package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebControllers {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/messages")
    public String chat(Authentication authentication, Model model) {

        String username = authentication.getName();

        model.addAttribute("username", username);

        return "messages";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        User userFromDB = userService.getByUsername(user.getUsername());

        if (userFromDB != null) {
            return "registration";
        }

        userService.save(user);

        return "redirect:/";
    }
}
