package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.model.GenericRS;
import com.simbirsoft.chat.model.UserDTO;
import com.simbirsoft.chat.service.MessageService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseBody
    public GenericRS deleteUser(@PathVariable Long id){
        userService.delete(id);
        return new GenericRS();
    }

    @GetMapping("/block/{id}")
    @ResponseBody
    public GenericRS blockUser(@PathVariable Long id){
        userService.blockUser(id);
        return new GenericRS();
    }

    @GetMapping("/unblock/{id}")
    @ResponseBody
    public GenericRS unblockUser(@PathVariable Long id){
        userService.unblockUser(id);
        return new GenericRS();
    }

    @GetMapping("/roles/{id}")
    @ResponseBody
    public GenericRS setModerator(@PathVariable Long id){
        userService.makeModerator(id);
        return new GenericRS();
    }

    @DeleteMapping("/roles/{id}")
    @ResponseBody
    public GenericRS removeModerator(@PathVariable Long id){
        userService.removeModerator(id);
        return new GenericRS();
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
    @ResponseBody
    public GenericRS deleteMessage(@PathVariable Long id){
        messageService.delete(id);
        return new GenericRS();
    }
}
