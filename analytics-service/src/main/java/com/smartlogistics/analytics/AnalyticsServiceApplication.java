package com.smartlogistics.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(
    exclude = {
      org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
      org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
    })
@EnableKafka
@EnableDiscoveryClient
public class AnalyticsServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(AnalyticsServiceApplication.class, args);
  }
}
