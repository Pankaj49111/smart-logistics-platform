package com.smartlogistics.aiassistant.controller;

import com.smartlogistics.aiassistant.service.LogisticsAiService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai/logistics")
public class LogisticsController {

  private final LogisticsAiService svc;

  public LogisticsController(LogisticsAiService svc) {
    this.svc = svc;
  }

  @PostMapping("/route-advice")
  public Mono<String> routeAdvice(@RequestBody String scenario) {
    return svc.routeAdvice(scenario);
  }

  @PostMapping("/eta-predict")
  public Mono<String> etaPredict(@RequestBody String payload) {
    return svc.etaPredict(payload);
  }

  @PostMapping("/delay-reason")
  public Mono<String> delayReason(@RequestBody String payload) {
    return svc.delayReason(payload);
  }
}
