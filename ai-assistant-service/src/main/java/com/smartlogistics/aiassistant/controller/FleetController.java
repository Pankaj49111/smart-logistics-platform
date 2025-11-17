package com.smartlogistics.aiassistant.controller;

import com.smartlogistics.aiassistant.service.FleetAiService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai/fleet")
public class FleetController {

  private final FleetAiService svc;

  public FleetController(FleetAiService svc) {
    this.svc = svc;
  }

  @PostMapping("/diagnose")
  public Mono<String> diagnose(@RequestBody String symptoms) {
    return svc.diagnose(symptoms);
  }

  @PostMapping("/predict-failure")
  public Mono<String> predictFailure(@RequestBody String metrics) {
    return svc.predictFailure(metrics);
  }

  @PostMapping("/fuel-optimization")
  public Mono<String> fuelOpt(@RequestBody String scenario) {
    return svc.fuelOptimization(scenario);
  }

  @PostMapping("/report")
  public Mono<String> report(@RequestBody String params) {
    return svc.report(params);
  }
}
