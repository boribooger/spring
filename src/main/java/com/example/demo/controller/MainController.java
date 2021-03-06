package com.example.demo.controller;

import com.example.demo.domen.User;
import com.example.demo.repo.MessagesRepo;
import com.example.demo.domen.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessagesRepo messagesRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messagesRepo.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messagesRepo.findByTag(filter);
        } else {
            messages = messagesRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag, user);
        messagesRepo.save(message);

        Iterable<Message> messages = messagesRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;

        if(filter != null && !filter.isEmpty()) {
            messages = messagesRepo.findByTag(filter);
        }
        else {
            messages = messagesRepo.findAll();
        }
        model.put("messages", messages);
        return "main";
    }
}