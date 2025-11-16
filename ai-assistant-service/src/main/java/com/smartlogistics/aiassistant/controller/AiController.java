package com.smartlogistics.aiassistant.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final OllamaChatModel chatModel;

    public AiController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody String message) {
        return chatModel.call(new Prompt(message));
    }

    @PostMapping("/summarize")
    public ChatResponse summarize(@RequestBody String text) {
        return chatModel.call(new Prompt("Summarize this:\n\n" + text));
    }

    @PostMapping("/analyze")
    public ChatResponse analyze(@RequestBody String event) {
        return chatModel.call(new Prompt("Analyze this logistics event:\n\n" + event));
    }
}
