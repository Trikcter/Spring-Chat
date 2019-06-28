package com.simbirsoft.chat.controllers;

import com.simbirsoft.chat.service.MessageService;
import com.simbirsoft.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminPanel")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminPanelController {
    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @GetMapping
    public String index(Model model){
        return "/adminPanel";
    }


}
