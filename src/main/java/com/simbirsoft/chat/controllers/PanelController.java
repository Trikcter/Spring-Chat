package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.model.GenericRs;
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

// TODO : Сделать адекватную проверку на роль Администратора, что бы его не выводило
// TODO Не работает вывод прав пользователя, надо сделать реактивно

@Controller
@RequestMapping("/panel")
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
public class PanelController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping
    public String getPanel(Authentication authentication, Model model) {
        List<UserDTO> users = userService.getAll();
        boolean isSuperuser = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                isSuperuser = true;
                break;
            }
        }

        model.addAttribute("users", users);
        model.addAttribute("isSuperuser", isSuperuser);

        return "panelControl";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public GenericRs deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new GenericRs();
    }

    @GetMapping("/block/{id}")
    @ResponseBody
    public GenericRs blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return new GenericRs();
    }

    @GetMapping("/unblock/{id}")
    @ResponseBody
    public GenericRs unblockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return new GenericRs();
    }

    @GetMapping("/roles/{id}")
    @ResponseBody
    public GenericRs setModerator(@PathVariable Long id) {
        userService.makeModerator(id);
        return new GenericRs();
    }

    @DeleteMapping("/roles/{id}")
    @ResponseBody
    public GenericRs removeModerator(@PathVariable Long id) {
        userService.removeModerator(id);
        return new GenericRs();
    }

    @GetMapping("/add")
    public String addUser() {
        return "/addUser";
    }

    @PostMapping("/add")
    public String addUser(UserDTO userDTO) {
        userService.addUser(userDTO);
        return "redirect:/panel";
    }

    @DeleteMapping("/messages/{id}")
    @ResponseBody
    public GenericRs deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
        return new GenericRs();
    }
}
