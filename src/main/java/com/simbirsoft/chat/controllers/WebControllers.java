package com.simbirsoft.chat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebControllers {
    @PostMapping(path = "/messages")
    public String index(HttpServletRequest request, String username, Model model) {

        if (username != null) {
            username = username.trim();

            model.addAttribute("username", username);
        }

        return "messages";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
