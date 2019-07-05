package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.model.GenericRS;
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
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/block/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void blockUser(@PathVariable Long id){
        userService.blockUser(id);
    }

    @GetMapping("/unblock/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void unblockUser(@PathVariable Long id){
        userService.unblockUser(id);
    }

    @GetMapping("/roles/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void setModerator(@PathVariable Long id){
        userService.makeModerator(id);
    }

    @DeleteMapping("/roles/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeModerator(@PathVariable Long id){
        userService.removeModerator(id);
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
