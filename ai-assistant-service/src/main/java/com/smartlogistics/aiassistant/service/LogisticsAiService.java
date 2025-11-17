package com.smartlogistics.aiassistant.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LogisticsAiService {

  private final AiPromptService ai;

  public LogisticsAiService(AiPromptService ai) {
    this.ai = ai;
  }

  // Prompt templates as constants
  private static final String ROUTE_PROMPT_TEMPLATE =
      "You are an expert logistics planner. Given this scenario:\n\n%s\n\n"
          + "Produce:\n"
          + "1) Three prioritized route options (title + steps) with pros & cons for each (time, cost, reliability).\n"
          + "2) Short recommendation (1 line) with rationale.\n"
          + "Return JSON with keys: options (array), recommended (object).\n"
          + "Keep strings short and precise.";

  private static final String ETA_PROMPT_TEMPLATE =
      "You are an ETA estimator. Given this structured input:\n\n%s\n\n"
          + "Estimate an arrival time (ISO 8601) and give a confidence between 0 and 1, and a short reasoning.\n"
          + "Return JSON: {\"eta\":\"<ISO8601>\",\"confidence\":0.##,\"explanation\":\"...\"}";

  private static final String DELAY_PROMPT_TEMPLATE =
      "You are a logistics incident analyst. Analyze the following event or log and list the top 3 probable reasons for the delay and suggested mitigations for each:\n\n%s\n\n"
          + "Return a short JSON: {\"reasons\":[{\"reason\":\"...\",\"mitigation\":\"...\"}, ...]}";

  public Mono<String> routeAdvice(String scenario) {
    String prompt = String.format(ROUTE_PROMPT_TEMPLATE, scenario);
    return ai.singleReply(prompt);
  }

  public Mono<String> etaPredict(String jsonPayload) {
    String prompt = String.format(ETA_PROMPT_TEMPLATE, jsonPayload);
    return ai.singleReply(prompt);
  }

  public Mono<String> delayReason(String eventText) {
    String prompt = String.format(DELAY_PROMPT_TEMPLATE, eventText);
    return ai.singleReply(prompt);
  }
}
