# Kafka Application

- [Overview](#overview)
- [Configuration](#configuration)
- [Components](#components)
  - [KafkaConfig](#kafkaconfig)
  - [MessageConsumer](#messageconsumer)
  - [KafkaController](#kafkacontroller)
  - [MessageProducer](#messageproducer)
  - [User Model](#user-model)
- [Testing](#testing)
- [Theory](#theory)

## Overview
This application demonstrates a Spring Boot Kafka implementation with multiple topics and consumer groups.

## Configuration
`application.yml`:

```yaml
spring:
  application:
    name: kafkapp
  main:
    allow-bean-definition-overriding: true
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
      properties:
        spring.deserializer.value.delegate.class: org.springframework.kafka.support.serializer.JsonDeserializer
        spring.json.trusted.packages: "com.vishav.kafkapp.model"
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    listener:
      missing-topics-fatal: false


# Testing
Use these cURL commands to test the application:

- Send to topic1 (kafka-demo-1)
  ```curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"John Doe\",\"age\":30}" http://localhost:8080/api/kafka/send/topic1```

- Send to topic2 (kafka-demo-2)
  ```curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Jane Doe\",\"age\":25}" http://localhost:8080/api/kafka/send/topic2```

- Send to topic3 (kafka-demo-3)
  ```curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Bob Smith\",\"age\":40}" http://localhost:8080/api/kafka/send/topic3```
