package com.smartlogistics.aiassistant.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InventoryAiService {

  private final AiPromptService ai;

  public InventoryAiService(AiPromptService ai) {
    this.ai = ai;
  }

  public Mono<String> demandForecast(String payload) {
    String prompt =
        "Forecast demand using the following data:\n\n"
            + payload
            + "\n\nReturn JSON with forecast and explanation.";
    return ai.singleReply(prompt);
  }

  public Mono<String> reorderAdvice(String payload) {
    String prompt =
        "Given inventory snapshot:\n\n"
            + payload
            + "\n\nAdvise reorder points and quantities. Return JSON.";
    return ai.singleReply(prompt);
  }

  public Mono<String> analyzeStock(String payload) {
    String prompt = "Explain anomalies in this stock data:\n\n" + payload;
    return ai.singleReply(prompt);
  }
}
