package com.smartlogistics.aiassistant.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FleetAiService {

  private final AiPromptService ai;

  public FleetAiService(AiPromptService ai) {
    this.ai = ai;
  }

  public Mono<String> diagnose(String symptoms) {
    String prompt =
        "You are a vehicle mechanic. Diagnose the following symptoms and provide likely causes and tests:\n\n"
            + symptoms;
    return ai.singleReply(prompt);
  }

  public Mono<String> predictFailure(String metricsJson) {
    String prompt =
        "Given these metrics (JSON):\n\n"
            + metricsJson
            + "\n\nReturn JSON: {\"probability\":0.##, \"recommendation\":\"...\"}";
    return ai.singleReply(prompt);
  }

  public Mono<String> fuelOptimization(String scenario) {
    String prompt = "Suggest 5 actionable fuel-efficiency tips for this scenario:\n\n" + scenario;
    return ai.singleReply(prompt);
  }

  public Mono<String> report(String params) {
    String prompt = "Generate a small fleet report given:\n\n" + params;
    return ai.singleReply(prompt);
  }
}
