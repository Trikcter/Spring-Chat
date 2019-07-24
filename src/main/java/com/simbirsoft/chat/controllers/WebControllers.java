package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.entity.Room;
import com.simbirsoft.chat.entity.User;
import com.simbirsoft.chat.service.RoomService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Controller
public class WebControllers {

    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    @GetMapping(path = "/messages")
    public String chat(Authentication authentication, Model model) {

        String username = authentication.getName();
        boolean isSuperuser = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if ((grantedAuthority.getAuthority().equals("ADMIN")) || (grantedAuthority.getAuthority().equals("MODERATOR"))) {
                isSuperuser = true;
                break;
            }
        }

        Set<Room> rooms = roomService.getRoomsByUsername(username);
        Room activeRoom = roomService.getActiveRoom(username);

        model.addAttribute("username", username);
        model.addAttribute("isSuperuser", isSuperuser);
        model.addAttribute("rooms", rooms);
        model.addAttribute("activeRoom",activeRoom.getName());

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
        Optional<User> userFromDB = userService.getByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            return "registration";
        }

        userService.save(user);

        return "redirect:/";
    }

}
