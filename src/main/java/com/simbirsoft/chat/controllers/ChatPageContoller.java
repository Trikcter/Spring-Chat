package com.simbirsoft.chat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatPageContoller {
    @RequestMapping(path = "/messages",method = RequestMethod.POST)
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "") String username, Model model){
        username = username.trim();

        model.addAttribute("username",username);
        return "messages";
    }
}
