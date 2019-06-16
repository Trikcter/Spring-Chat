package com.simbirsoft.chat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatPageContoller {
    @GetMapping("/messages")
    public String index(){
        return "messages";
    }
}
