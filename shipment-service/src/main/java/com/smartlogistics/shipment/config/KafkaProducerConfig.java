package com.smartlogistics.shipment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogistics.shipment.events.ShipmentEvent;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

  @Bean
  public ProducerFactory<String, ShipmentEvent> producerFactory(ObjectMapper objectMapper) {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    JsonSerializer<ShipmentEvent> jsonSerializer = new JsonSerializer<>(objectMapper);
    jsonSerializer.setAddTypeInfo(false); // 🚀 THE CRITICAL FIX

    return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), jsonSerializer);
  }

  @Bean
  public KafkaTemplate<String, ShipmentEvent> kafkaTemplate(
      ProducerFactory<String, ShipmentEvent> pf) {
    return new KafkaTemplate<>(pf);
  }
}
