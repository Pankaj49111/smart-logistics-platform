package com.smartlogistics.aiassistant.controller;

import com.smartlogistics.aiassistant.dto.ChatRequest;
import com.smartlogistics.aiassistant.dto.ChatResponse;
import com.smartlogistics.aiassistant.service.AiPromptService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai")
public class AiController {

  private final AiPromptService ai;

  public AiController(AiPromptService ai) {
    this.ai = ai;
  }

  @PostMapping("/chat")
  public Mono<ChatResponse> chat(@RequestBody ChatRequest req) {
    long start = System.currentTimeMillis();
    return ai.singleReply(req.message())
        .map(reply -> new ChatResponse(reply, "ollama", System.currentTimeMillis() - start));
  }

  @PostMapping("/summarize")
  public Mono<String> summarize(@RequestBody String text) {
    String p = "Summarize the following text in 3 concise bullet points:\n\n" + text;
    return ai.singleReply(p);
  }

  @PostMapping("/analyze")
  public Mono<String> analyze(@RequestBody String event) {
    String p = "Analyze the following and provide 3 insights:\n\n" + event;
    return ai.singleReply(p);
  }

  @PostMapping("/explain")
  public Mono<String> explain(@RequestBody String content) {
    String p = "Explain for a senior Java engineer (root cause + fix) this content:\n\n" + content;
    return ai.singleReply(p);
  }

  @PostMapping("/keywords")
  public Mono<String> keywords(@RequestBody String text) {
    String p = "Extract up to 8 keywords (comma separated) from this text:\n\n" + text;
    return ai.singleReply(p);
  }
}
