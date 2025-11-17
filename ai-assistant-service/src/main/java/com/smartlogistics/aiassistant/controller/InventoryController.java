package com.smartlogistics.aiassistant.controller;

import com.smartlogistics.aiassistant.service.InventoryAiService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai/inventory")
public class InventoryController {

  private final InventoryAiService svc;

  public InventoryController(InventoryAiService svc) {
    this.svc = svc;
  }

  @PostMapping("/demand-forecast")
  public Mono<String> demandForecast(@RequestBody String payload) {
    return svc.demandForecast(payload);
  }

  @PostMapping("/reorder-advice")
  public Mono<String> reorder(@RequestBody String payload) {
    return svc.reorderAdvice(payload);
  }

  @PostMapping("/analyze-stock")
  public Mono<String> analyzeStock(@RequestBody String payload) {
    return svc.analyzeStock(payload);
  }
}
