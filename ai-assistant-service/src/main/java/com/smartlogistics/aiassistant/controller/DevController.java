package com.smartlogistics.aiassistant.controller;

import com.smartlogistics.aiassistant.service.DevAiService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai/debug")
public class DevController {

  private final DevAiService svc;

  public DevController(DevAiService svc) {
    this.svc = svc;
  }

  @PostMapping("/logs")
  public Mono<String> logs(@RequestBody String logs) {
    return svc.summarizeLogs(logs);
  }

  @PostMapping("/error-fix")
  public Mono<String> errorFix(@RequestBody String stack) {
    return svc.fixError(stack);
  }

  @PostMapping("/testcases")
  public Mono<String> genTests(@RequestBody String code) {
    return svc.generateTests(code);
  }

  @PostMapping("/sql")
  public Mono<String> toSql(@RequestBody String nl) {
    return svc.toSql(nl);
  }

  @PostMapping("/refactor")
  public Mono<String> refactor(@RequestBody String code) {
    return svc.refactor(code);
  }
}
