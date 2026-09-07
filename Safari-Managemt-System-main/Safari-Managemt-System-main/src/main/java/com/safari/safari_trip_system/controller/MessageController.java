package com.safari.safari_trip_system.controller;

import com.safari.safari_trip_system.entity.Message;
import com.safari.safari_trip_system.service.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 1. Tourist View: My Messages (Chat with Admin)
    @GetMapping("/messages")
    public String showTouristMessages(Model model, Authentication authentication) {
        String touristUsername = authentication.getName();
        // This automatically marks Admin's messages as read
        List<Message> chat = messageService.getConversation(touristUsername, "admin");

        model.addAttribute("messages", chat);
        model.addAttribute("receiverName", "admin");
        return "chat";
    }

    // 2. Admin View: Chat with specific Tourist
    @GetMapping("/admin/messages")
    public String showAdminChat(@RequestParam("tourist") String touristUsername, Model model, Authentication authentication) {
        // This automatically marks Tourist's messages as read
        List<Message> chat = messageService.getConversation("admin", touristUsername);

        model.addAttribute("messages", chat);
        model.addAttribute("receiverName", touristUsername);
        return "chat";
    }

    // 3. Process Sending Message
    @PostMapping("/messages/send")
    public String sendMessage(@RequestParam("receiver") String receiverUsername,
                              @RequestParam("content") String content,
                              Authentication authentication) {

        String senderUsername = authentication.getName();
        messageService.sendMessage(senderUsername, receiverUsername, content);

        if (senderUsername.equals("admin")) {
            return "redirect:/admin/messages?tourist=" + receiverUsername;
        } else {
            return "redirect:/messages";
        }
    }

    // 4. Delete Message (NEW)
    @PostMapping("/messages/delete")
    public String deleteMessage(@RequestParam("messageId") Long messageId,
                                @RequestParam("receiver") String receiverUsername,
                                Authentication authentication) {

        String currentUsername = authentication.getName();

        try {
            messageService.deleteMessage(messageId, currentUsername);
        } catch (Exception e) {
            // In a real app, we would pass an error message.
            // For now, we just reload the page.
        }

        if (currentUsername.equals("admin")) {
            return "redirect:/admin/messages?tourist=" + receiverUsername;
        } else {
            return "redirect:/messages";
        }
    }
}