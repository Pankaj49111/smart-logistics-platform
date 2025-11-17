package com.smartlogistics.aiassistant.service;

import java.time.Duration;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AiPromptService {

  private final OllamaChatModel model;

  public AiPromptService(OllamaChatModel model) {
    this.model = model;
  }

  /** Call the model with a prompt and enforce timeout + simple fallback message. */
  public Mono<String> singleReply(String prompt) {
    return Mono.fromCallable(() -> model.call(prompt))
        .timeout(Duration.ofSeconds(25))
        .onErrorResume(ex -> Mono.just("AI temporarily unavailable: " + ex.getMessage()));
  }
}
