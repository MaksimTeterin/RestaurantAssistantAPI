package com.example.restaurantassistantrestapi.controller;

import com.example.restaurantassistantrestapi.service.AIToolService;
import com.example.restaurantassistantrestapi.service.ChatBotService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ChatBotController {

    private final ChatBotService chatBotService;
    private final AIToolService aiToolService;

    public ChatBotController(ChatBotService chatBotService, AIToolService aiToolService) {
        this.chatBotService = chatBotService;
        this.aiToolService = aiToolService;
    }

    @GetMapping("ask-ai/{restaurantId}")
    public String askAi(@RequestParam() String prompt, @PathVariable() int restaurantId, @RequestParam() String userEmail) {
        System.out.println("askAi function called");
        System.out.println("Prompt: " + prompt);
        return chatBotService.getResponse(prompt, restaurantId, userEmail);
    }

}
