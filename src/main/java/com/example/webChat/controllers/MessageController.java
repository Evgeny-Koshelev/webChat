package com.example.webChat.controllers;

import com.example.webChat.entities.Message;
import com.example.webChat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/chat")
public class MessageController {

    private final List<String> activeUsers = new ArrayList<>();

    @Autowired
    private MessageService messageService;

    @GetMapping
    public String chatPage(Model model) {
        List<Message> messages = messageService.getLastNMessages();
        Collections.reverse(messages);
        model.addAttribute("messages", messages);
        if(activeUsers.isEmpty())
            userJoined();
        model.addAttribute("activeUsers", activeUsers);
        return "chat";
    }


    @MessageMapping("/join")
    private void userJoined() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        activeUsers.add(username);
    }

    @MessageMapping("/leave")
    public void userLeft() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        activeUsers.remove(username);
    }


}

