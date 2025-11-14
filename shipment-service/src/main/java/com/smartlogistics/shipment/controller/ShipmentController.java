package com.smartlogistics.shipment.controller;

import com.smartlogistics.shipment.events.KafkaShipmentProducer;
import com.smartlogistics.shipment.events.ShipmentEvent;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

  @Autowired private KafkaShipmentProducer producer;

  @GetMapping("/test")
  public String test() {
    return "Shipment Service is up ✅";
  }

  @GetMapping("/publish")
  public ResponseEntity<String> publishEvent() {
    ShipmentEvent event =
        new ShipmentEvent("SHP-001", "CREATED", "pankaj", LocalDateTime.now().toString());
    producer.publishShipmentEvent(event);
    return ResponseEntity.ok("Event published ✅");
  }
}
