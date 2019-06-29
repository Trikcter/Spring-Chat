package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.model.UserDTO;
import com.simbirsoft.chat.service.MessageService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/panel")
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('MODERATOR') or hasAuthority('ROLE_MODERATOR')")
public class PanelController {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @GetMapping
    public String index(Authentication authentication,Model model) {
        List<UserDTO> users = userService.getAll();
        Boolean isGod = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isGod = true;
                break;
            }
        }

        model.addAttribute("users",users);
        model.addAttribute("isGod",isGod);

        return "panelControl";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username){
        userService.delete(username);
        return "redirect:/panel";
    }

    @GetMapping("/block/{username}")
    public String blockUser(@PathVariable String username){
        userService.blockUser(username);
        return "redirect:/panel";
    }

    @GetMapping("/unblock/{username}")
    public String unBlockUser(@PathVariable String username){
        userService.unBlockUser(username);
        return "redirect:/panel";
    }

    @GetMapping("/editRoles/{username}")
    public String setModerator(@PathVariable String username){
        userService.makeModerator(username);
        return "redirect:/panel";
    }

    @GetMapping("/deleteRoles/{username}")
    public String removeModerator(@PathVariable String username){
        userService.removeModerator(username);
        return "redirect:/panel";
    }

    @GetMapping("/add")
    public String addUser(){
        return "/addUser";
    }

    @PostMapping("/add")
    public String addUser(UserDTO userDTO){
        userService.addUser(userDTO);
        return "redirect:/panel";
    }
}
