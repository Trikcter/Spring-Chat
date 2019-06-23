package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.model.Role;
import com.simbirsoft.chat.model.Users;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
public class WebControllers {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/messages")
    public String toChat(HttpServletRequest request, String username, Model model) {

        if (username != null) {
            username = username.trim();

            model.addAttribute("username", username);
        }

        return "messages";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String toReg() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Users user) {
        Users userFromDB = userService.getByusername(user.getUsername());

        if (userFromDB != null) {
            return "registration";
        } else {
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userService.addUser(user);

            return "redirect:/login";
        }

    }
}
