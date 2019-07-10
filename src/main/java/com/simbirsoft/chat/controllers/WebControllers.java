package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@Controller
public class WebControllers {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/messages")
    public String chat(Authentication authentication, Model model) {

        String username = authentication.getName();
        boolean isSuperuser = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if ((grantedAuthority.getAuthority().equals("ROLE_ADMIN")) || (grantedAuthority.getAuthority().equals("ROLE_MODERATOR"))) {
                isSuperuser = true;
                break;
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("isSuperuser", isSuperuser);

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
        User userFromDB = userService.getByUsername(user.getUsername()).orElse(new User());

        if (userFromDB != null) {
            return "registration";
        }

        userService.save(user);

        return "redirect:/";
    }

}
