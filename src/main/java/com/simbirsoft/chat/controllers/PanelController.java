package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.model.UserDTO;
import com.simbirsoft.chat.service.MessageService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/panel")
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
public class PanelController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping
    public String getPanel(Authentication authentication,Model model) {
        List<UserDTO> users = userService.getAll();
        boolean isSuperuser = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isSuperuser = true;
                break;
            }
        }

        model.addAttribute("users",users);
        model.addAttribute("isSuperuser",isSuperuser);

        return "panelControl";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/panel";
    }

    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable Long id){
        userService.blockUser(id);
        return "redirect:/panel";
    }

    @GetMapping("/unblock/{username}")
    public String unblockUser(@PathVariable Long id){
        userService.unblockUser(id);
        return "redirect:/panel";
    }

    @GetMapping("/roles/{username}")
    public String setModerator(@PathVariable Long id){
        userService.makeModerator(id);
        return "redirect:/panel";
    }

    @DeleteMapping("/roles/{username}")
    public String removeModerator(@PathVariable Long id){
        userService.removeModerator(id);
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

    @DeleteMapping("/messages/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteMessage(@PathVariable Long id){
        messageService.delete(id);
    }
}
