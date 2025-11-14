package com.smartlogistics.analytics.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShipmentEventConsumer {

  @KafkaListener(topics = "shipment-events", groupId = "analytics-group")
  public void consume(ShipmentEvent event) {
    System.out.println("📥 Received Shipment Event in Analytics Service: " + event);
  }
}
