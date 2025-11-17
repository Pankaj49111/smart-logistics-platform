package com.smartlogistics.aiassistant.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DevAiService {

  private final AiPromptService ai;

  public DevAiService(AiPromptService ai) {
    this.ai = ai;
  }

  public Mono<String> summarizeLogs(String logs) {
    String prompt =
        "You are an SRE. Summarize the following logs and provide 3 likely root causes and 3 recommended fixes:\n\n"
            + logs;
    return ai.singleReply(prompt);
  }

  public Mono<String> fixError(String stacktrace) {
    String prompt = "Explain this stacktrace and provide step-by-step fixes:\n\n" + stacktrace;
    return ai.singleReply(prompt);
  }

  public Mono<String> generateTests(String code) {
    String prompt = "Generate JUnit 5 test cases for the following Java method(s):\n\n" + code;
    return ai.singleReply(prompt);
  }

  public Mono<String> toSql(String nl) {
    String prompt =
        "Convert this natural language request to a parameterized Postgres SQL query and mention indexes:\n\n"
            + nl;
    return ai.singleReply(prompt);
  }

  public Mono<String> refactor(String code) {
    String prompt =
        "Refactor the following Java code for readability and performance. Return only the refactored code:\n\n"
            + code;
    return ai.singleReply(prompt);
  }
}
