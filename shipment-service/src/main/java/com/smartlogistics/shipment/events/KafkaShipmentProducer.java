package com.smartlogistics.shipment.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaShipmentProducer {

  private static final String TOPIC = "shipment-events";

  @Autowired private KafkaTemplate<String, ShipmentEvent> kafkaTemplate;

  public void publishShipmentEvent(ShipmentEvent event) {
    kafkaTemplate.send(TOPIC, event);
    System.out.println("📤 Published event to Kafka: " + event);
  }
}
