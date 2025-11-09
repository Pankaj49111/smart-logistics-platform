package com.smartlogistics.shipment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

  @GetMapping("/test")
  public String test() {
    return "Shipment Service is up ✅";
  }
}
